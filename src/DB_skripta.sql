CREATE PROCEDURE BrisanjeDrzavaById
    @DefaultId INT
AS
BEGIN
    DELETE FROM Drzava
    WHERE IdDrzava >= @DefaultId;
END;
