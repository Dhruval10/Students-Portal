CREATE OR REPLACE VIEW SportsView
AS
	SELECT ItemName, UPPER(Availability) "Available",
	StudentRegNo "Registration # of Student",
	(SELECT TO_CHAR(Sports."Date", 'HH24:MM:SS') FROM Dual) "Time of Issue"
	FROM SPORTS
/
