package com.virtualpairprogrammers.roombooking.services;

import com.virtualpairprogrammers.roombooking.RoombookingApplication;
import com.virtualpairprogrammers.roombooking.data.BookingRepository;
import com.virtualpairprogrammers.roombooking.data.RoomRepository;
import com.virtualpairprogrammers.roombooking.data.UserRepository;
import com.virtualpairprogrammers.roombooking.model.*;
import com.virtualpairprogrammers.roombooking.model.entities.Booking;
import com.virtualpairprogrammers.roombooking.model.entities.LayoutCapacity;
import com.virtualpairprogrammers.roombooking.model.entities.Room;
import com.virtualpairprogrammers.roombooking.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataInitialization {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired    
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.size() == 0) {
            Room blueRoom = new Room("Blue meeting room","1st Floor");
            blueRoom.setCapacity(new LayoutCapacity(Layout.BOARD,8));
            blueRoom.setCapacity(new LayoutCapacity(Layout.THEATER,16));
            roomRepository.save(blueRoom);

            Room redRoom = new Room("Red meeting room","2nd Floor");
            redRoom.setCapacity(new LayoutCapacity(Layout.BOARD,12));
            redRoom.setCapacity(new LayoutCapacity(Layout.USHAPE,26));
            roomRepository.save(redRoom);

            Room confRoom = new Room("Main Conference Room","1st Floor");
            confRoom.setCapacity(new LayoutCapacity(Layout.THEATER,80));
            confRoom.setCapacity(new LayoutCapacity(Layout.USHAPE,40));
            roomRepository.save(confRoom);

            
            User user = new User("matt", bCryptPasswordEncoder.encode("maradona"));
            user.addAuthority("ROLE_ADMIN");
//            user.addAuthority("ROLE_USER");
            user.addAuthority("ROLE_COLLABORATOR");
//            user.addAuthority("ADMIN");
//            user.addAuthority("USER");
//            user.addAuthority("COLLABORATOR");

            userRepository.save(user);
            
            User user2 = new User("hammad", bCryptPasswordEncoder.encode("ronaldo"));
            user2.addAuthority("ROLE_USER");
            userRepository.save(user2);
            
            
            Booking booking1 = new Booking();
            booking1.setDate(new java.sql.Date(new java.util.Date().getTime()));
            booking1.setStartTime(java.sql.Time.valueOf("11:00:00"));
            booking1.setEndTime(java.sql.Time.valueOf("11:30:00"));
            booking1.setLayout(Layout.USHAPE);
            booking1.setParticipants(8);
            booking1.setTitle("Conference call with CEO");
            booking1.setRoom(blueRoom);
            booking1.setUser(user);
            bookingRepository.save(booking1);

            Booking booking2 = new Booking();
            booking2.setDate(new java.sql.Date(new java.util.Date().getTime()));
            booking2.setStartTime(java.sql.Time.valueOf("13:00:00"));
            booking2.setEndTime(java.sql.Time.valueOf("14:30:00"));
            booking2.setLayout(Layout.BOARD);
            booking2.setParticipants(5);
            booking2.setTitle("Sales Update");
            booking2.setRoom(redRoom);
            booking2.setUser(user);
            bookingRepository.save(booking2);
            
            
            RoombookingApplication.logger.info("# of users: {}", userRepository.count());

            RoombookingApplication.logger.info("All users unsorted:");
	        List<User> users = userRepository.findAll();
	        RoombookingApplication.logger.info("{}", users);

	        RoombookingApplication.logger.info("------------------------");

        }
    }
}
