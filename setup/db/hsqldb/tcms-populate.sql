-- Cleanup
DELETE FROM Answers;
DELETE FROM Questionnaires
DELETE FROM Questions;
DELETE FROM RegistrationDatePricingRules;
DELETE FROM GroupPricingRules;
DELETE FROM Reminders;
DELETE FROM UserRoles;
DELETE FROM Roles;
DELETE FROM ScheduleEntries; 
DELETE FROM Sessions;
DELETE FROM Presentations;
DELETE FROM PresentationTopics;
DELETE FROM PresentationLevels;
DELETE FROM PresentationTypes;
DELETE FROM Attendees;
DELETE FROM Presenters;
DELETE FROM Users;
DELETE FROM Abstracts;
DELETE FROM AbstractStatus;
DELETE FROM Booths;
DELETE FROM Rooms;
DELETE FROM Tracks;
DELETE FROM Conferences;
DELETE FROM Venues;
DELETE FROM Addresses;
DELETE FROM News;

-- Populate
INSERT INTO Addresses ( pk_Id, StreetAddress, City, State, ZipCode ) 
  VALUES( 0, '747 Howard Street', 'San Francisco', 'CA', '94103' );
INSERT INTO Addresses ( pk_Id, StreetAddress, City, State, ZipCode ) 
  VALUES( 1, '204 Bluestone Ct.', 'Westerville', 'OH', '43081' );
INSERT INTO Addresses ( pk_Id, StreetAddress, City, State, ZipCode ) 
  VALUES( 2, '685 Farrington Drive', 'Worthington', 'OH', '43085' );
INSERT INTO Addresses ( pk_Id, StreetAddress, City, State, ZipCode ) 
  VALUES( 3, '204 Bluestone Ct.', 'Westerville', 'OH', '43081' );
INSERT INTO Addresses ( pk_Id, StreetAddress, City, State, ZipCode ) 
  VALUES( 4, '685 Farrington Drive', 'Worthington', 'OH', '43085' );

INSERT INTO Venues ( pk_Id, fk_AddressId, Name, Phone, Fax ) 
  VALUES ( 0, 0, 'The Moscone Center', '415.974.4000', '415.974.4073' );

INSERT INTO Conferences ( pk_Id, Name, fk_VenueId ) 
  VALUES ( 0, 'Open Source Java Conference', 0 );

INSERT INTO Tracks ( pk_Id, fk_ConferenceId, Title, Subtitle, Description ) 
  VALUES ( 0, 0, 'J2SE', 'The Java 2 Standard Edition', '');
INSERT INTO Tracks ( pk_Id, fk_ConferenceId, Title, Subtitle, Description ) 
  VALUES ( 1, 0, 'J2EE', 'The Java 2 Enterprise Edition', '');
INSERT INTO Tracks ( pk_Id, fk_ConferenceId, Title, Subtitle, Description ) 
  VALUES ( 2, 0, 'J2ME', 'The Java 2 Micro Edition', '');
INSERT INTO Tracks ( pk_Id, fk_ConferenceId, Title, Subtitle, Description ) 
  VALUES ( 3, 0, 'JINI', 'The Jini Network Technology', '');

INSERT INTO Rooms ( pk_Id, fk_VenueId, Name, Notes, Capacity ) 
  VALUES ( 0, 0, 'Blue Room', '', 250 );
INSERT INTO Rooms ( pk_Id, fk_VenueId, Name, Notes, Capacity ) 
  VALUES ( 1, 0, 'Red Room', '', 40 );
INSERT INTO Rooms ( pk_Id, fk_VenueId, Name, Notes, Capacity ) 
  VALUES ( 2, 0, 'Yellow Room', '', 25 );
INSERT INTO Rooms ( pk_Id, fk_VenueId, Name, Notes, Capacity ) 
  VALUES ( 3, 0, 'Pink Room', '', 150 );
INSERT INTO Rooms ( pk_Id, fk_VenueId, Name, Notes, Capacity ) 
  VALUES ( 4, 0, 'Brown Room', '', 200 );
INSERT INTO Rooms ( pk_Id, fk_VenueId, Name, Notes, Capacity ) 
  VALUES ( 5, 0, 'Green Room', '', 225 );
INSERT INTO Rooms ( pk_Id, fk_VenueId, Name, Notes, Capacity ) 
  VALUES ( 6, 0, 'Purple Room', '', 100 );

INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 0, 0, '', '10x10' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 1, 0, '', '12x18' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 2, 0, '', '12x18' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 3, 0, '', '10x10' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 4, 0, '', '10x20' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 5, 0, '', '8x10' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 6, 0, '', '10x10' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 7, 0, '', '8x10' );
INSERT INTO Booths ( pk_Id, fk_VenueId, Notes, Size ) VALUES ( 8, 0, '', '8x10' );

INSERT INTO AbstractStatus ( pk_Id, Name ) VALUES ( 0, 'IN-REVIEW' );
INSERT INTO AbstractStatus ( pk_Id, Name ) VALUES ( 1, 'ACCEPTED' );
INSERT INTO AbstractStatus ( pk_Id, Name ) VALUES ( 2, 'REJECTED' );

INSERT INTO Users 
  ( pk_Id, Password, FirstName, LastName, Email, HomePhone, WorkPhone, Fax, fk_AddressId )
VALUES 
  ( 0, 'pswd', 'Anne', 'Sam', 'aks@isllc.com', '555.5777', '555.5919', '', 1 );

INSERT INTO Users 
  ( pk_Id, Password, FirstName, LastName, Email, HomePhone, WorkPhone, Fax, fk_AddressId ) VALUES 
  ( 1, 'pswd', 'Sue', 'Judd', 'sjudd@jsllc.com', '555.4119', '555.4119', '', 2 );

INSERT INTO Users 
  ( pk_Id, Password, FirstName, LastName, Email, HomePhone, WorkPhone, Fax, fk_AddressId ) VALUES 
  ( 2, 'pswd', 'Brian', 'Sam-Bodden', 'bsb@isllc.com', '555.0154', '', '', 3 );

INSERT INTO Users 
  ( pk_Id, Password, FirstName, LastName, Email, HomePhone, WorkPhone, Fax, fk_AddressId ) VALUES 
  ( 3, 'pswd', 'Christopher', 'Judd', 'cjudd@js.com', '555.4119', '555.4119', '', 4 );

INSERT INTO Presenters ( pk_Id, fk_UserId, Company, Bio) VALUES (0, 2, 'Integrallis Software, LLC.', 'Brian knows Java');
INSERT INTO Presenters ( pk_Id, fk_UserId, Company, Bio) VALUES (1, 3, 'Judd Solutions, LLC.', 'Chris also knows Java');

INSERT INTO Attendees ( pk_Id, fk_UserId ) VALUES ( 0, 0 );
INSERT INTO Attendees ( pk_Id, fk_UserId ) VALUES ( 1, 1 );

INSERT INTO PresentationTypes ( pk_Id, Name, Description ) 
  VALUES ( 0, 'REGULAR', 'Regular Session' );
INSERT INTO PresentationTypes ( pk_Id, Name, Description ) 
  VALUES ( 1, 'TUTORIAL', 'Preconference Tutorial' );
INSERT INTO PresentationTypes ( pk_Id, Name, Description ) 
  VALUES ( 2, 'PANEL', 'Panel Discussion' );
INSERT INTO PresentationTypes ( pk_Id, Name, Description ) 
  VALUES ( 3, 'CASE', 'Case Study' );
INSERT INTO PresentationTypes ( pk_Id, Name, Description ) 
  VALUES ( 4, 'KEYNOTE', 'Keynote' );
INSERT INTO PresentationTypes ( pk_Id, Name, Description ) 
  VALUES ( 5, 'BOF', 'Birds of a Feather' );

INSERT INTO PresentationLevels ( pk_Id, Name, Description ) 
  VALUES ( 0, 'Beginner', 'For Greenhorns' );
INSERT INTO PresentationLevels ( pk_Id, Name, Description ) 
  VALUES ( 1, 'Intermediate', 'For those who make a living out of it' );
INSERT INTO PresentationLevels ( pk_Id, Name, Description ) 
  VALUES ( 2, 'Advanced', 'For Gurus' );

INSERT INTO PresentationTopics  ( pk_Id, Name, Description ) VALUES ( 0, 'Performance', '' );
INSERT INTO PresentationTopics  ( pk_Id, Name, Description ) VALUES ( 1, 'Profiling', '' );
INSERT INTO PresentationTopics  ( pk_Id, Name, Description ) VALUES ( 2, 'Testing', '' );
INSERT INTO PresentationTopics  ( pk_Id, Name, Description ) VALUES ( 3, 'Design', '' );
INSERT INTO PresentationTopics  ( pk_Id, Name, Description ) VALUES ( 4, 'Productivity', '' );
INSERT INTO PresentationTopics  ( pk_Id, Name, Description ) VALUES ( 5, 'Management', '' );
INSERT INTO PresentationTopics  ( pk_Id, Name, Description ) VALUES ( 6, 'Development', '' );


INSERT INTO Roles ( pk_Id, RoleName, Description ) VALUES ( 0, 'ATTENDEE', 'Attendees' );
INSERT INTO Roles ( pk_Id, RoleName, Description ) VALUES ( 1, 'PRESENTER', 'Presenters' );

INSERT INTO UserRoles ( pk_Id, fk_UserId, fk_Role ) VALUES ( 0, 0, 0 );
INSERT INTO UserRoles ( pk_Id, fk_UserId, fk_Role ) VALUES ( 1, 1, 0 );
INSERT INTO UserRoles ( pk_Id, fk_UserId, fk_Role ) VALUES ( 2, 2, 1 );
INSERT INTO UserRoles ( pk_Id, fk_UserId, fk_Role ) VALUES ( 3, 3, 1 );

-- sqldb format 'yyyy-mm-dd hh:mm:ss.fffffffff'
INSERT INTO RegistrationDatePricingRules ( pk_PricingModel, StartDate, EndDate, Price ) VALUES ( 'Early Bird', '2004-01-01 00:00:00.000000000', '2004-02-01 00:00:00.000000000', '1850.00');

INSERT INTO RegistrationDatePricingRules ( pk_PricingModel, StartDate, EndDate, Price ) VALUES ( 'Pre-Registration', '2004-02-02 00:00:00.000000000', '2004-03-01 00:00:00.000000000', '1975.00');

INSERT INTO RegistrationDatePricingRules ( pk_PricingModel, StartDate, EndDate, Price ) VALUES ( 'On-site', '2004-03-02 00:00:00.000000000', '2004-04-01 00:00:00.000000000', '2000.00' );

INSERT INTO GroupPricingRules 
  ( pk_RuleName, MinimumAttendees, MaximumAttendees, DiscountPerAttendee ) 
  VALUES ('5to10', 5, 10, '50.00' );
INSERT INTO GroupPricingRules 
  ( pk_RuleName, MinimumAttendees, MaximumAttendees, DiscountPerAttendee ) 
VALUES ('10to20', 10, 20, '100.00' );
INSERT INTO GroupPricingRules 
  ( pk_RuleName, MinimumAttendees, MaximumAttendees, DiscountPerAttendee ) 
VALUES ('20orMore', 20, 10000, '200.00' );

INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (0, 'Generics in JavaTM', 0, 6, 0, 'Generic types are slated for incorporation into the JavaTM programming language in JDKTM 1.5 release. In this talk, we highlight key features of the generics extension and discuss tradeoffs between functionality, compatibility, and performance.', 1, 0);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (1, 'Advanced RMI', 0, 6, 0, 'Tricks, strategies, and idioms that JavaTM Remote Method Invocation (Java RMI) programmers use in the field to achieve high performance and reliability.', 1, 1);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (2, 'JCP', 0, 6, 0, 'The JCP services defines a process of proposing, developing, and maintaining new Java technology standards.', 1, 0);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (3, 'Concurrency Utilities', 0, 6, 0, 'Many programs become clearer, shorter, faster, easier to write, and more reliable if, instead of starting from scratch, you use higher-level synchronization and coordination constructs.', 1, 1);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (4, 'Five Secrets to Faster Code', 0, 6, 0, 'This session explores ways to ensure that your code performs at its peak with real-world lessons', 1, 0);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (5, 'Performance Tuning J2EE', 0, 6, 0, 'A scalable and extensible framework in which JavaTM technology performance tuning can be automated for enterprise applications.', 1, 1);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (6, 'Open Source J2EE Gems', 0, 6, 0, 'Reviews the most prominent Open Source J2EE Tools', 1, 0);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (7, 'Logging with Log4J', 0, 6, 0, 'Efficient logging in J2EE with Log4J', 1, 1);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (8, 'Struts 101', 0, 6, 0, 'Building the Web Tier with Struts', 1, 0);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (9, 'Middlegen', 0, 6, 0, 'CMP Entity Bean generation with Middlegen', 1, 1);
INSERT INTO Abstracts (pk_Id, Title, Type, Topic, Level, Body, Status, fk_PresenterId) 
  VALUES (10, 'J2EE Testing with Cactus', 0, 6, 0, 'In-server Testing techniques with Cactus', 1, 0);


INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (0, 0);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (1, 1);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (2, 2);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (3, 3);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (4, 4);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (5, 5);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (6, 6);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (7, 7);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (8, 8);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (9, 9);
INSERT INTO Presentations (pk_Id, fk_AbstractId) VALUES (10, 10);

INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (0, 0, 0, '2004-05-01 12:00:00.000000000', '2004-05-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (1, 1, 1, '2004-05-01 12:00:00.000000000', '2004-05-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (2, 2, 2, '2004-05-01 12:00:00.000000000', '2004-05-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (3, 3, 3, '2004-05-01 12:00:00.000000000', '2004-05-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (4, 4, 4, '2004-05-01 12:00:00.000000000', '2004-05-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (5, 5, 5, '2004-05-01 12:00:00.000000000', '2004-05-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (6, 6, 6, '2004-05-01 12:00:00.000000000', '2004-05-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (7, 7, 0, '2004-05-02 13:45:00.000000000', '2004-05-02 14:45:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (8, 8, 1, '2004-05-02 13:45:00.000000000', '2004-05-02 14:45:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (9, 9, 2, '2004-05-02 13:45:00.000000000', '2004-05-02 14:45:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (10, 10, 3, '2004-05-01 13:45:00.000000000', '2004-05-01 14:45:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (11, 3, 3, '2004-06-01 12:00:00.000000000', '2004-06-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (12, 4, 4, '2004-06-01 12:00:00.000000000', '2004-06-01 13:00:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (13, 7, 0, '2004-06-02 13:45:00.000000000', '2004-06-02 14:45:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (14, 8, 1, '2004-06-02 13:45:00.000000000', '2004-06-02 14:45:00.000000000');
INSERT INTO Sessions (pk_Id, fk_PresentationId, fk_RoomId, DateTimeBegin, DateTimeEnd) VALUES (15, 9, 2, '2004-06-02 13:45:00.000000000', '2004-06-02 14:45:00.000000000');

INSERT INTO News (pk_Id, Date, RemoveDate, CreationDate, Published, Title, Body) VALUES (0, '2004-06-02 00:00:00.000000000', '2004-06-02 00:00:00.000000000', '2004-06-02 00:00:00.000000000', 'true', 'News 0', 'News Item Number 0');
INSERT INTO News (pk_Id, Date, RemoveDate, CreationDate, Published, Title, Body) VALUES (1, '2004-06-03 00:00:00.000000000', '2004-06-02 00:00:00.000000000', '2004-06-02 00:00:00.000000000', 'true', 'News 1', 'News Item Number 1');
INSERT INTO News (pk_Id, Date, RemoveDate, CreationDate, Published, Title, Body) VALUES (2, '2004-06-04 00:00:00.000000000', '2004-06-02 00:00:00.000000000', '2004-06-02 00:00:00.000000000', 'true', 'News 2', 'News Item Number 2');
INSERT INTO News (pk_Id, Date, RemoveDate, CreationDate, Published, Title, Body) VALUES (3, '2004-06-04 00:00:00.000000000', '2004-06-02 00:00:00.000000000', '2004-06-02 00:00:00.000000000', 'true', 'News 3', 'News Item Number 3');
INSERT INTO News (pk_Id, Date, RemoveDate, CreationDate, Published, Title, Body) VALUES (4, '2004-06-05 00:00:00.000000000', '2004-06-02 00:00:00.000000000', '2004-06-02 00:00:00.000000000', 'true', 'News 4', 'News Item Number 4');





