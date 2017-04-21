package com.centrixlink.cus.dao;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.centrixlink.cus.bean.RedisSession;
import com.centrixlink.log.Logging;
import com.centrixlink.util.Constants;

@Repository
public class SessionDao {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public void saveSession(final RedisSession session) {
		
		stringRedisTemplate.expire(session.getSession_id().toString(),
				Constants.REDIS_TIMEOUT, TimeUnit.SECONDS);
		
		stringRedisTemplate.opsForSet().add(session.getSession_id().toString(),
				session.toString());
	}
	
	public void updateExpire(String session_id) {
		stringRedisTemplate.expire(session_id, Constants.REDIS_TIMEOUT, TimeUnit.SECONDS);
	}

	public void delSession(final String session_id) {
		stringRedisTemplate.delete(session_id);
		
	}
	
	public long getExpire(String session_id) {
		return stringRedisTemplate.getExpire(session_id);
	}

	public JSONObject querySession(final String session_id) {
		if(!stringRedisTemplate.hasKey(session_id)){
			Logging.error("redis key not exist.");
			return null;
		}
		
		Set<String> set = stringRedisTemplate.opsForSet().members(session_id);

		JSONObject obj = null;

		if (set.isEmpty()) {
			Logging.info("nothing for session, session_id: " + session_id);
			return null;
		}

		Iterator<String> it = set.iterator();
		if (it.hasNext()) {
			String str = it.next();
			obj = JSONObject.fromObject(str);
			Logging.info("Redis: " + str);
		}
		return obj;
	}

}
