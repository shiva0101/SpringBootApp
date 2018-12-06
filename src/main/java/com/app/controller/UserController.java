package com.app.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.app.model.User;
import com.app.model.User_Dept;
import com.app.scheduleThread.MyThread;
import com.app.service.UserService;
import com.app.userRepository.UserDeptRepo;
import com.google.gson.Gson;

/**
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */
@RestController
@RequestMapping("/user")
@Configurable
public class UserController {

	public static int flag = 1;
	public static long jobId;
	/**
	 * Marks a field, as to be autowired by Spring's dependency injection facilities
	 * here userService has been Autowired
	 */
	@Autowired
	UserService userService;

	@Autowired
	Environment environment;
	
	@Autowired
	UserDeptRepo udRepo;

	// Created the Logger Object to get logging info
	final static Logger logger = Logger.getLogger(UserController.class);

	Map<String, Long> params = new HashMap<>();
	Set<Thread> threadSet = new HashSet<>();

	/**
	 * This API simply starts the thread endpoint of this API is startThread and
	 * 
	 * @returns the JobId
	 */
	@RequestMapping(value = "/startThread")
	public long startThread() {

		Thread thread1 = new Thread(new MyThread());
		thread1.start();
		logger.info("startThread ID is " + thread1.getId());
		threadSet.add(thread1);
		long jobId = thread1.getId();
		System.out.println("Id is " + jobId);
		return jobId;
	}

	/**
	 * This API simply stops the thread by accepting jobId of any thread endpoint of
	 * this API is stopThread and
	 * 
	 * @returns
	 */
	@RequestMapping(value = "/stopThread/{jobId}")
	public String stopThread(@PathVariable("jobId") long jobId) {
		// threadSet.stream().findAny(t->t.getId()==id).ifPresent(Thread::interrupt);
		for (Thread thread : threadSet) {
			if (thread.getId() == jobId) {
				logger.info("Interrupting the Thread");
				thread.interrupt();
				logger.info("Interruped");

			}
		}
		return " Successfully Stopped Thread by id" + jobId;
	}

	/**
	 * This method simply fetch the user by id param
	 * 
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 */
	
	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserById(@PathVariable("id") long id) throws ClassNotFoundException {
		ArrayList<Object> data = new ArrayList<>();
		Object deptResult;
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}

		logger.info("Fetching User with id" + id);
		long myId = user.getId();
		
		List<User_Dept> udList=udRepo.findAll(myId);
		Iterator<User_Dept> itr1 = udList.iterator();
		while (itr1.hasNext()) {
			User_Dept ud1 = itr1.next();
		
		//	long params1=ud1.getDept_id();
		long ids=ud1.getDept_id();
		Set<Long> idSet=new HashSet<>();
		idSet.add(ud1.getDept_id());
		Map<String, Long> params1 = new HashMap<>();
		params1.put("ids", ids);
			final String uri = "http://localhost:9999/dept/get/{ids}";
			RestTemplate restTemplate = new RestTemplate();
		
			String result = restTemplate.getForObject(uri, String.class, params1);
			
			Gson g = new Gson();
			deptResult = g.fromJson(result, Object.class);
			data.add(deptResult);
		}

/*		List<User_Dept> udList = udRepo.findAll();
		Iterator<User_Dept> itr1 = udList.iterator();
		while (itr1.hasNext()) {
			User_Dept ud1 = itr1.next();
			ud1.getDept_id();
			ud1.getUser_id();
			while (myId == ud1.getUser_id()) {
				params.put("ids", ud1.getDept_id());
				final String uri = "http://localhost:9999/dept/get/{ids}";
				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class, params);
				Gson g = new Gson();
				deptResult = g.fromJson(result, Object.class);
				data.add(deptResult);
			}
		}*/

		// data.add(deptResult);
		data.add(user);
		return new ResponseEntity<Object>(data, HttpStatus.OK);
	}

	/*@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserById(@PathVariable("id") long id) throws ClassNotFoundException {

		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}

		ArrayList<Object> data = new ArrayList<>();

		logger.info("Fetching User with id" + id);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		long myId = user.getId();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shiva1", "root", "");

		) {
			String sql = "select * from user_dept where user_id =" + myId;
			java.sql.PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				params.put("ids", rs.getLong(2));
				System.out.println(rs.getLong(1) + " " + rs.getLong(2));
				final String uri = "http://localhost:9999/dept/get/{ids}";
				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class, params);
				logger.info("Result is " + result);
				Gson g = new Gson();
				Object deptResult = g.fromJson(result, Object.class);
				data.add(deptResult);
				logger.info("Result is............ " + deptResult);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		data.add(user);
		return new ResponseEntity<Object>(data, HttpStatus.OK);
	}*/

	/**
	 * this method simply fetch List of users from database and
	 * 
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @returns the List of users.
	 */

	/*	@GetMapping(value = "/get", headers = "Accept=application/json")
	public List<Object> getAllUser() throws ClassNotFoundException, InterruptedException {
		Object deptResult;
		List<User> tasks = userService.getUser();
		List<Object> obj = new ArrayList<>();
		Iterator<User> itr = tasks.iterator();

		while (itr.hasNext()) {
			User user = itr.next();
			long myId = user.getId();
			Class.forName("com.mysql.jdbc.Driver");
			try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shiva1", "root", "");

			) {

				String sql = "select * from user_dept where user_id =" + myId;
				java.sql.PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while (rs.next()) {
					params.put("ids", rs.getLong(2));
					System.out.println(rs.getLong(1) + " " + rs.getLong(2));
					final String uri = "http://localhost:9999/dept/get/{ids}";
					RestTemplate restTemplate = new RestTemplate();
					String result = restTemplate.getForObject(uri, String.class, params);
					Gson g = new Gson();
					deptResult = g.fromJson(result, Object.class);
					obj.add(deptResult);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		obj.add(tasks);

		return obj;

	}
*/
	 Map<String, Long> params1 = new HashMap<>();
	@Autowired
 private UserDeptRepo uddao;
	@GetMapping(value = "/get", headers = "Accept=application/json")
	public List<Object> getAllUser() throws ClassNotFoundException, InterruptedException {
		Object deptResult;
		List<User> tasks = userService.getUser();
		List<Object> obj = new ArrayList<>();
		Iterator<User> itr = tasks.iterator();

		while (itr.hasNext()) {
			User user = itr.next();
			
			long myId = user.getId();
			
		User_Dept udd=uddao.findOne(myId);
			//List<User_Dept> udList=udRepo.findAll(myId);
			//Iterator<User_Dept> itr1 = udList.iterator();
			//while (itr1.hasNext()) {
				//User_Dept ud1 = itr1.next();
			
			//	long params1=ud1.getDept_id();
		long ids=	udd.getDept_id();
			 
				
					params1.put("ids", ids);
					final String uri = "http://localhost:9999/dept/get/{ids}";
					RestTemplate restTemplate = new RestTemplate();
				
					String result = restTemplate.getForObject(uri, String.class, params1);
					
					Gson g = new Gson();
					deptResult = g.fromJson(result, Object.class);
					obj.add(deptResult);
			
			
			
			
			}
		
		

		obj.add(tasks);

		return obj;

	}
	
	
	/**
	 * updates the user
	 * 
	 * @param currentUser
	 * @returns status code 200 if user updated successfully
	 */
	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<String> updateUser(@Valid @RequestBody User currentUser, BindingResult result) {
		if (result.hasErrors()) {
			return updateUser(currentUser, result);
		}
		logger.info("updating the user");
		User user = userService.findById(currentUser.getId());
		if (user == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		userService.update(currentUser, currentUser.getId());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * This method simply deletes the user by
	 * 
	 * @param id and
	 * @return 204 if user is deleted successfully
	 * @throws ClassNotFoundException
	 */
	@DeleteMapping(value = "delete/{id}", headers = "Accept=application/json")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) throws ClassNotFoundException {
		User user = userService.findById(id);

		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		Class.forName("com.mysql.jdbc.Driver");
		long myId = user.getId();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shiva1", "root", "");

		) {

			String sql = "delete from user_dept where user_id =" + myId;
			java.sql.PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	/**
	 * This method simply creates the user
	 * 
	 * @param user
	 * @param ucBuilder
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user, UriComponentsBuilder ucBuilder,
			BindingResult result) throws ClassNotFoundException, SQLException {

		logger.info("Creating the User");
		user = userService.createUser(user);

		if (result.hasErrors()) {
			return createUser(user, ucBuilder, result);
		}
		String uri = "http://localhost:9999/dept/createDept";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject(uri, Object.class, String.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());

	/*	Client client=Client.create();
		WebResource webResource=client.resource(uri);
		ClientResponse clientResponse=webResource.accept("application/json").post(ClientResponse.class);
		if(true) {
			String output=clientResponse.getEntity(String.class);
			logger.info("output is " +output);
		}*/
		return new ResponseEntity<User>(headers, HttpStatus.CREATED);
	}

	/**
	 * This API simply get the id and name by using annotation @QueryParam
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@GetMapping(value = "/{id}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUsers(@PathVariable("id") Long id, @PathVariable("name") String name) {

		logger.info("Fetching User with id " + id);
		logger.info("Fetching User with name " + name);

		return " Got Query param with Id :" + id + " And name :" + name;
	}

	/**
	 * This API simply get the name and age
	 * 
	 * @param name
	 * @param age
	 * @returns the name and id if the API is working fine.
	 */
	@PostMapping(value = "/add", headers = "Accept=application/json")
	public Response addUser(@FormParam("name") String name, @FormParam("age") Integer age) {

		return Response.status(200).entity("addUser is called, name : " + name + ", age : " + age).build();

	}

	/**
	 * This method simply returns the paginated users by requested param
	 * 
	 * @param start returns the start index
	 * @param size  returns the size of records
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<User> getPaginatedUsers(@QueryParam(value = "start") Long start, @QueryParam("size") Long size) {
		if (start == null) {
			start = 1L;
		}

		logger.info("Entering The getPaginatedUsers method");

		if (start >= 0 && size > 0) {
			logger.info("in If Statement");

			return userService.getAllPaginatedUsers(start, size);
		}

		logger.error("size should be greater than 0");
		return null;

	}

	/**
	 * This method simply reads the properties of application.properties file
	 * 
	 * @return list of property
	 */
	@RequestMapping(value = "/propsFile", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> readPropertyFile() {

		String port = environment.getProperty("server.port");
		String driver = environment.getProperty("spring.datasource.driver");
		String url = environment.getProperty("spring.datasource.url");
		String pass = environment.getProperty("spring.datasource.password");
		ArrayList<String> envList = new ArrayList<>();
		envList.add("port : " + port);
		envList.add("driver : " + driver);
		envList.add("url " + url);
		envList.add("pass " + pass);

		return envList;
	}

}