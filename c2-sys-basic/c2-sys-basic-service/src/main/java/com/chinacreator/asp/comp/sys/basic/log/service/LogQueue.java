package com.chinacreator.asp.comp.sys.basic.log.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.asp.comp.sys.basic.log.dto.LogDTO;

public class LogQueue extends Thread {

	// 日志队列
	private static BlockingQueue<LogDTO> s_LOGQUEUE = new LinkedBlockingQueue<LogDTO>(
			2000);

	@Autowired
	private LogService logService;

	/**
	 * 在队列尾插入日志
	 * 
	 * @param logDTO
	 *            日志数据传输对象
	 */
	public static void offer(LogDTO logDTO) {
		try {
			s_LOGQUEUE.offer(logDTO, 2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				LogDTO logDTO = LogQueue.s_LOGQUEUE.poll(2, TimeUnit.SECONDS);
				if (null != logDTO) {
					logService.create(logDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
