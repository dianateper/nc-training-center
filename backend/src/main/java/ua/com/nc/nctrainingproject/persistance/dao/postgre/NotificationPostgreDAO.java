package ua.com.nc.nctrainingproject.persistance.dao.postgre;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.nc.nctrainingproject.models.Notification;
import ua.com.nc.nctrainingproject.persistance.dao.AbstractDAO;
import ua.com.nc.nctrainingproject.persistance.dao.NotificationsDAO;
import ua.com.nc.nctrainingproject.persistance.dao.postgre.queries.NotificationQuery;
import ua.com.nc.nctrainingproject.persistance.mappers.NotificationRowMapper;

import java.util.List;

public class NotificationPostgreDAO extends AbstractDAO<Notification> implements NotificationsDAO {

	public NotificationPostgreDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public Notification getNotificationByUserID(int userId) {
		List<Notification> notifications = jdbcTemplate.query(NotificationQuery.GET_BY_USER_ID,
				new NotificationRowMapper(), userId);
		if (notifications.size() == 0) {
			return null;
		}
		return notifications.get(0);
	}

	@Override
	public Notification getNotificationByActionID(int actionId) {
		List<Notification> notifications = jdbcTemplate.query(NotificationQuery.GET_BY_ACTION_ID,
				new NotificationRowMapper(), actionId);
		if (notifications.size() == 0) {
			return null;
		}
		return notifications.get(0);
	}

	@Override
	public List<Notification> getAllNotifications() {
		return jdbcTemplate.query(NotificationQuery.GET_ALL_NOTIFICATIONS, new NotificationRowMapper());
	}

	@Override
	public void deleteNotification(int userId, int actionId) {
		jdbcTemplate.query(NotificationQuery.DELETE_BY_USER_ACTION_ID,
				new NotificationRowMapper(), userId, actionId);
	}

	@Override
	public void createNotification(Notification notification) {
		jdbcTemplate.update(NotificationQuery.CREATE_NOTIFICATION,
				notification.getUserId(), notification.getActionId());
	}
}
