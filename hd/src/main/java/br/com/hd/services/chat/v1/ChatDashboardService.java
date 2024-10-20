package br.com.hd.services.chat.v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hd.data.vo.chat.dashboard.v1.RoomCustomerQuantityVO;
import br.com.hd.data.vo.chat.dashboard.v1.RoomEmployeeQuantityVO;
import br.com.hd.data.vo.chat.dashboard.v1.RoomPriorityQuantityVO;
import br.com.hd.data.vo.chat.dashboard.v1.RoomStatusQuantityVO;
import br.com.hd.data.vo.chat.dashboard.v1.SectorMonthQuantityVO;
import br.com.hd.exceptions.generic.v1.InvalidArgumentsException;
import br.com.hd.model.chat.room.v1.RoomStatus;
import br.com.hd.repositories.chat.v1.ChatDashboardRepository;

@Service
public class ChatDashboardService {
	
	@Autowired
	private ChatDashboardRepository repository;
	
	public List<RoomPriorityQuantityVO> roomPriorityQuantity(String initialDate, String endDate) {
		
		List<RoomPriorityQuantityVO> voList = new ArrayList<>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Date iDate;
		Date eDate;
		try {
			iDate = formatter.parse(initialDate);
			eDate = formatter.parse(endDate);
		} catch (ParseException e) {
			throw new InvalidArgumentsException("Could not convert the dates (" + initialDate + " - " + endDate + ")! Reason (" + e.getMessage() + ")");
		}
		
		List<Object[]> result = repository.findRoomPriorityQuantityByPeriod(iDate, eDate);
		
		for (Object[] row : result) {
			RoomPriorityQuantityVO vo = new RoomPriorityQuantityVO(
				(String) row[0], 
				(Long) row[1]
			);
			voList.add(vo);
		}
		
		return voList;
	}
	
	public List<SectorMonthQuantityVO> sectorMonthQuantity(Integer year) {
		
		List<SectorMonthQuantityVO> voList = new ArrayList<>();
		
		List<Object[]> result = repository.findSectorMonthQuantityByYear(year);
		
		for (Object[] row : result) {
			SectorMonthQuantityVO vo = new SectorMonthQuantityVO(
	            (String) row[0],
	            (Long) row[1],
	            (Long) row[2],
	            (Long) row[3],
	            (Long) row[4],
	            (Long) row[5],
	            (Long) row[6],
	            (Long) row[7],
	            (Long) row[8], 
	            (Long) row[9], 
	            (Long) row[10], 
	            (Long) row[11],
	            (Long) row[12]
	        );
			
			voList.add(vo);
		}
		
		return voList;
	}
	
	public List<RoomEmployeeQuantityVO> roomEmployeeQuantity(String initialDate, String endDate) {
		
		List<RoomEmployeeQuantityVO> voList = new ArrayList<>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Date iDate;
		Date eDate;
		try {
			iDate = formatter.parse(initialDate);
			eDate = formatter.parse(endDate);
		} catch (ParseException e) {
			throw new InvalidArgumentsException("Could not convert the dates (" + initialDate + " - " + endDate + ")! Reason (" + e.getMessage() + ")");
		}
		
		List<Object[]> result = repository.findRoomEmployeeQuantityByPeriod(iDate, eDate);
		
		for (Object[] row : result) {
			RoomEmployeeQuantityVO vo = new RoomEmployeeQuantityVO(
				(String) row[0], 
				(Long) row[1]
			);
			voList.add(vo);
		}
		
		return voList;
	}
	
	public List<RoomCustomerQuantityVO> roomCustomerQuantity(String initialDate, String endDate) {
		
		List<RoomCustomerQuantityVO> voList = new ArrayList<>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Date iDate;
		Date eDate;
		try {
			iDate = formatter.parse(initialDate);
			eDate = formatter.parse(endDate);
		} catch (ParseException e) {
			throw new InvalidArgumentsException("Could not convert the dates (" + initialDate + " - " + endDate + ")! Reason (" + e.getMessage() + ")");
		}
		
		List<Object[]> result = repository.findRoomCustomerQuantityByPeriod(iDate, eDate);
		
		for (Object[] row : result) {
			RoomCustomerQuantityVO vo = new RoomCustomerQuantityVO(
				(String) row[0], 
				(Long) row[1]
			);
			voList.add(vo);
		}
		
		return voList;
	}
	
	public List<RoomStatusQuantityVO> roomStatusQuantity(String initialDate, String endDate) {
		
		List<RoomStatusQuantityVO> voList = new ArrayList<>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Date iDate;
		Date eDate;
		try {
			iDate = formatter.parse(initialDate);
			eDate = formatter.parse(endDate);
		} catch (ParseException e) {
			throw new InvalidArgumentsException("Could not convert the dates (" + initialDate + " - " + endDate + ")! Reason (" + e.getMessage() + ")");
		}
		
		List<Object[]> result = repository.findRoomStatusQuantityByPeriod(iDate, eDate);
		
		for (Object[] row : result) {

			RoomStatus status = (Integer) row[0] == 0 ? RoomStatus.Open 
					: (Integer) row[0] == 1 ? RoomStatus.Chatting
					: (Integer) row[0] == 2 ? RoomStatus.Paused
					: (Integer) row[0] == 3 ? RoomStatus.Transferred
					: RoomStatus.Closed;
			
			RoomStatusQuantityVO vo = new RoomStatusQuantityVO(
				status, 
				(Long) row[1]
			);
			voList.add(vo);
		}
		
		return voList;
	}
	
}
