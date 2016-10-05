CREATE TABLE Addresses (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  StreetAddress VARCHAR(64), 
  AptNumber VARCHAR(32), 
  City VARCHAR(32), 
  State CHAR(2), 
  ZipCode VARCHAR(10)
);

CREATE TABLE Venues (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_AddressId INTEGER, 
  Name VARCHAR(32), 
  Phone VARCHAR(12), 
  Fax VARCHAR(12),
  CONSTRAINT VenuesAddressesFK FOREIGN KEY(fk_AddressId) REFERENCES Addresses(pk_Id)   
);

CREATE TABLE Conferences (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Name varchar(64), 
  Description LONGVARCHAR,
  StartDate DATETIME,
  EndDate DATETIME,
  AbstractSubmissionStartDate DATETIME,
  AbstractSubmissionEndDate DATETIME,  
  fk_VenueId int,
  CONSTRAINT ConferencesVenuesFK FOREIGN KEY(fk_VenueId) REFERENCES Venues(pk_Id)   
);

CREATE TABLE Tracks (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_ConferenceId INTEGER, 
  Title VARCHAR(32), 
  Subtitle VARCHAR(32), 
  Description LONGVARCHAR,
  CONSTRAINT TracksConferencesFK FOREIGN KEY(fk_ConferenceId) REFERENCES Conferences(pk_Id) 
);

CREATE TABLE Rooms (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_VenueId INTEGER,
  Name VARCHAR(32), 
  Notes VARCHAR(64), 
  Capacity INTEGER,
  CONSTRAINT RoomsVenuesFK FOREIGN KEY(fk_VenueId) REFERENCES Venues(pk_Id)
);

CREATE TABLE Booths (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_VenueId INTEGER, 
  Notes VARCHAR(64), 
  Size VARCHAR(32),
  CONSTRAINT BoothsVenuesFK FOREIGN KEY(fk_VenueId) REFERENCES Venues(pk_Id)  
);

CREATE TABLE AbstractStatus (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Name VARCHAR(16) NOT NULL
);

CREATE TABLE Users (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Password VARCHAR(16), 
  FirstName VARCHAR(32), 
  LastName VARCHAR(32), 
  Email VARCHAR(64), 
  HomePhone VARCHAR(12), 
  WorkPhone VARCHAR(12), 
  Fax VARCHAR(12), 
  fk_AddressId INTEGER,
  CONSTRAINT UsersAddressesFK FOREIGN KEY(fk_AddressId) REFERENCES Addresses(pk_Id),
  CONSTRAINT UniqueEmail UNIQUE(Email)
);

CREATE TABLE Presenters (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_UserId INTEGER, 
  Company VARCHAR(64),
  Bio VARCHAR(255),
  CONSTRAINT PresentersUsersFK FOREIGN KEY(fk_UserId) REFERENCES Users(pk_Id),
  CONSTRAINT UniqueUserIds UNIQUE(fk_UserId)    
);

CREATE TABLE Abstracts (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Title VARCHAR(64),  
  Type INTEGER, 
  Topic INTEGER, 
  Level INTEGER, 
  Body LONGVARCHAR, 
  Status INTEGER,
  fk_PresenterId INTEGER,    
  CONSTRAINT PresentationsPresentersFK FOREIGN KEY(fk_PresenterId) REFERENCES Presenters(pk_Id)  
);

CREATE TABLE Attendees (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_UserId INTEGER, 
  CONSTRAINT PresentersUsersFK FOREIGN KEY(fk_UserId) REFERENCES Users(pk_Id),
  CONSTRAINT UniqueUserIds UNIQUE(fk_UserId)      
);

CREATE TABLE PresentationTypes (  
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Name VARCHAR(16) NOT NULL,
  Description VARCHAR(64)
);

CREATE TABLE PresentationLevels (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Name VARCHAR(16) NOT NULL, 	
  Description VARCHAR(64)  
);

CREATE TABLE PresentationTopics (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Name VARCHAR(16) NOT NULL, 	
  Description VARCHAR(64)
);

CREATE TABLE Presentations (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_AbstractId INTEGER, 
  CONSTRAINT PresentationsAbstractsFK FOREIGN KEY(fk_AbstractId) REFERENCES Abstracts(pk_Id)
);

CREATE TABLE Sessions (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_PresentationId INTEGER, 
  fk_RoomId INTEGER, 
  DateTimeBegin DATETIME, 
  DateTimeEnd DATETIME,
  CONSTRAINT SessionsPresentationsFK FOREIGN KEY(fk_PresentationId) REFERENCES Presentations(pk_Id),
  CONSTRAINT SessionsRoomsFK FOREIGN KEY(fk_RoomId) REFERENCES Rooms(pk_Id)
);

CREATE TABLE ScheduleEntries (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Name VARCHAR(64), 
  Description VARCHAR(32), 
  fk_SessionId INTEGER, 
  fk_UserId INTEGER, 
  CONSTRAINT ScheduleEntriesSessionsFK FOREIGN KEY(fk_SessionId) REFERENCES Sessions(pk_Id),
  CONSTRAINT ScheduleEntriesUsersFK FOREIGN KEY(fk_UserId) REFERENCES Users(pk_Id)    
);
  
CREATE TABLE Roles (
  pk_Id INTEGER NOT NULL PRIMARY KEY,     
  RoleName VARCHAR(16) NOT NULL,
  Description VARCHAR(64)
);

CREATE TABLE UserRoles (
  pk_Id INTEGER NOT NULL PRIMARY KEY,   
  fk_UserId INTEGER, 
  fk_Role INTEGER,
  CONSTRAINT UserRolesUsersFK FOREIGN KEY(fk_UserId) REFERENCES Users(pk_Id)
);

CREATE TABLE Reminders (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  fk_ScheduleEntryId INTEGER, 
  fk_UserId INTEGER, 
  Message VARCHAR(64), 
  DateAndTime DATETIME NOT NULL,
  CONSTRAINT RemindersScheduleEntriesFK FOREIGN KEY(fk_ScheduleEntryId) REFERENCES ScheduleEntries(pk_Id),    
  CONSTRAINT RemindersUsersFK FOREIGN KEY(fk_UserId) REFERENCES Users(pk_Id)
);

CREATE TABLE RegistrationDatePricingRules (
  pk_PricingModel VARCHAR(16) NOT NULL PRIMARY KEY,
  StartDate DATETIME NOT NULL,
  EndDate DATETIME NOT NULL,
  Price DOUBLE NOT NULL
);

CREATE TABLE GroupPricingRules (
  pk_RuleName VARCHAR(16) NOT NULL PRIMARY KEY,
  MinimumAttendees INTEGER,
  MaximumAttendees INTEGER,
  DiscountPerAttendee DOUBLE
);

CREATE TABLE Questions (
  pk_Id INTEGER NOT NULL PRIMARY KEY,
  QuestionText LONGVARCHAR,
  QuestionType INTEGER NOT NULL,
  QuestionChoices LONGVARCHAR
);

CREATE TABLE Questionnaires (
  pk_Id INTEGER NOT NULL PRIMARY KEY,
  fk_QuestionId INTEGER NOT NULL,
  fk_PresentationId INTEGER NOT NULL,
  CONSTRAINT QuestionnairesQuestionsFK FOREIGN KEY(fk_QuestionId) REFERENCES Questions(pk_Id),  
  CONSTRAINT QuestionnairesPresentationsFK FOREIGN KEY(fk_PresentationId) REFERENCES Presentations(pk_Id)
);

CREATE TABLE Answers (
  pk_Id INTEGER NOT NULL PRIMARY KEY,
  fk_UserId INTEGER NOT NULL,
  fk_QuestionnaireId INTEGER NOT NULL,
  CONSTRAINT AnswersUsersFK FOREIGN KEY(fk_UserId) REFERENCES Users(pk_Id),    
  CONSTRAINT AnswersQuestionnairesFK FOREIGN KEY(fk_QuestionnaireId) REFERENCES Questionnaires(pk_Id)  
);

CREATE TABLE News (
  pk_Id INTEGER NOT NULL PRIMARY KEY, 
  Date DATETIME NOT NULL,
  RemoveDate DATETIME NOT NULL,
  CreationDate DATETIME NOT NULL,
  Published BIT NOT NULL,
  Title VARCHAR(32),
  Body LONGVARCHAR	
);


 
