package ua.com.nc.nctrainingproject.persistance.dao.postgre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.nc.nctrainingproject.models.User;
import ua.com.nc.nctrainingproject.persistance.dao.AbstractDAO;
import ua.com.nc.nctrainingproject.persistance.dao.postgre.queries.UserQuery;
import ua.com.nc.nctrainingproject.persistance.mappers.UserRowMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserPostgreDAO extends AbstractDAO<User> {

	@Autowired
	public UserPostgreDAO(DataSource dataSource) {
		super(dataSource);
	}

	public User getUserById(int id) {
		List<User> users = jdbcTemplate.query(UserQuery.GET_BY_ID, new UserRowMapper(), id);
		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	public User getUserByUserName(String userName) {
		List<User> users = jdbcTemplate.query(UserQuery.GET_BY_USERNAME, new UserRowMapper(), userName);
		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	public void updateUserByName(String userName, User user) {
		jdbcTemplate.update(UserQuery.UPDATE_BY_USERNAME,
				user.getUserName(),
				user.getUserPassword(),
				user.getEmail(),
				userName);
	}

	public List<User> getAllUsers() {
		return jdbcTemplate.query(UserQuery.GET_ALL, new UserRowMapper());
	}

	public String getUserEmail(String email) {
		List<User> result = jdbcTemplate.query(UserQuery.GET_BY_EMAIL, new UserRowMapper(), email);
		if (result.size() == 0) {
			return null;
		}
		return result.get(0).getEmail();
	}

	public User getUserByEmail(String email) {
		List<User> users = jdbcTemplate.query(UserQuery.GET_BY_EMAIL, new UserRowMapper(), email);
		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	public void updatePassword(String password, String email) {
		jdbcTemplate.update(UserQuery.UPDATE_PASSWORD_BY_EMAIL, password, email);
	}

	public void createUser(User user) {
		jdbcTemplate.update(UserQuery.CREATE_USER, user.getUserName(), user.getUserPassword(), user.getEmail(), user.getUserRole());
	}

	public void createAdmin(User admin) {
		jdbcTemplate.update(UserQuery.CREATE_ADMIN, admin.getUserName(), admin.getUserPassword(), admin.getEmail(), admin.getUserRole());
	}

	public void activateAccount(String email) {
		jdbcTemplate.update(UserQuery.UPDATE_STATUS_BY_EMAIL, true, email);
	}

	public void checkAccountActivation(int hours) {
		jdbcTemplate.update(UserQuery.CHECK_ACCOUNT_ACTIVATION, hours);

	}

	public List<User> getUsersByRole(String userRole) {
		return jdbcTemplate.query(UserQuery.GET_USERS_BY_ROLE, new UserRowMapper(), userRole);
	}

	public void deactivateAccount(int id) {
		jdbcTemplate.update(UserQuery.DEACTIVATE_ACCOUNT, false, id);
	}

	public List<User> searchUsersByUsername(String search) {
		return jdbcTemplate.query(UserQuery.SEARCH_USERS_BY_USERNAME, new UserRowMapper(), "%" + search + "%");
	}

	public List<User> getAllAdmins() {
		return jdbcTemplate.query(UserQuery.GET_ALL_ADMINS, new UserRowMapper());
	}

	public List<User> getAllModerators() {
		return jdbcTemplate.query(UserQuery.GET_ALL_MODERATORS, new UserRowMapper());
	}

	public List<User> getActivatedModerators() {
		return jdbcTemplate.query(UserQuery.GET_MODERATORS_BY_ACTIVATED, new UserRowMapper());
	}

	public List<User> getActivatedAdmins() {
		return jdbcTemplate.query(UserQuery.GET_ADMIN_BY_ACTIVATED, new UserRowMapper());
	}
}
