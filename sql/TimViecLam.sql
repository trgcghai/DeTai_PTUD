CREATE DATABASE TimViecLam
USE TimViecLam

--CREATE TABLE Director(
--idDirector nvarchar(100) primary key,
--name nvarchar(255),
--birthday date
--)

--CREATE TABLE Movie(
--idMovie nvarchar(100) primary key,
--name nvarchar(255),
--time int,
--dateOfDebut date,
--type nvarchar(255),
--poster nvarchar(100),
--idDirector nvarchar(100) foreign key references Director(idDirector) on delete cascade on update cascade
--)

--CREATE TABLE Customer(
--idCustomer nvarchar(100) primary key,
--name nvarchar(255),
--birthday date,
--phone nvarchar(10),
--gender int
--)

CREATE TABLE TaiKhoan(
maTK int primary key,
email nvarchar(100),
matkhau nvarchar(100),
vaitro nvarchar(100)
)

CREATE TABLE NhanVien(
maNV int primary key,
tenNV nvarchar(255),
ngaysinh date,
diachi nvarchar(255),
gioitinh nvarchar(100),
sodienthoai nvarchar(10),
maTK int foreign key references TaiKhoan(maTK) on delete cascade on update cascade
)

--CREATE TABLE Room(
--idRoom nvarchar(100) primary key,
--numberRoom int
--)

--CREATE TABLE Screening(
--idScreening nvarchar(100) primary key,
--dateStart datetime,
--idMovie nvarchar(100) foreign key references Movie(idMovie) on delete cascade on update cascade,
--idRoom nvarchar(100) foreign key references Room(idRoom) on delete cascade on update cascade
--)

--CREATE TABLE Ticket(
--idTicket nvarchar(100) primary key,
--dateBook datetime,
--price float,
--idScreening nvarchar(100) foreign key references Screening(idScreening) on delete cascade on update cascade
--)

--CREATE TABLE Sitting(
--idSitting nvarchar(100) primary key,
--numberSitting nvarchar(255),
--state int,
--idRoom nvarchar(100) foreign key references Room(idRoom) on delete cascade on update cascade,
--idTicket nvarchar(100) foreign key references Ticket(idTicket) on delete no action on update no action
--)

--CREATE TABLE Bill(
--idBill nvarchar(100) primary key,
--dateBill date,
--total float,
--idEmployee nvarchar(100) foreign key references Employee(idEmployee) on delete cascade on update cascade,
--idCustomer nvarchar(100) foreign key references Customer(idCustomer) on delete cascade on update cascade,
--idTicket nvarchar(100) foreign key references Ticket(idTicket) on delete cascade on update cascade
--)

--INSERT INTO Room VALUES ('R1', 1)
--INSERT INTO Room VALUES ('R2', 2)
--INSERT INTO Room VALUES ('R3', 3)
--INSERT INTO Room VALUES ('R4', 4)
--INSERT INTO Room VALUES ('R5', 5)
--INSERT INTO Room VALUES ('R6', 6)
--INSERT INTO Room VALUES ('R7', 7)
--INSERT INTO Room VALUES ('R8', 8)

--GO
--DECLARE @seat_number INT = 1; -- Start seat number
--DECLARE @room_index INT = 1; -- Start room index (for 'R' values)
--WHILE @seat_number <= 400
--	BEGIN
--		DECLARE @g_value NVARCHAR(10);
--		DECLARE @a_value NVARCHAR(10);

--		SET @g_value = 'G' + CAST(@seat_number AS NVARCHAR(3));

--		DECLARE @a_index INT;
--		SET @a_index = ((@seat_number - 1) % 50) + 1;
--		IF @a_index <= 10
--			SET @a_value = 'A' + CAST(@a_index AS NVARCHAR(3));
--		ELSE IF @a_index <= 20
--			SET @a_value = 'B' + CAST((@a_index - 10) AS NVARCHAR(3));
--		ELSE IF @a_index <= 30
--			SET @a_value = 'C' + CAST((@a_index - 20) AS NVARCHAR(3));
--		ELSE IF @a_index <= 40
--			SET @a_value = 'D' + CAST((@a_index - 30) AS NVARCHAR(3));
--		ELSE IF @a_index <=50
--			SET @a_value = 'E' + CAST((@a_index - 40) AS NVARCHAR(3));

--		DECLARE @r_value NVARCHAR(10);
--		SET @r_value = 'R' + CAST(((@room_index-1) % 8) + 1 AS NVARCHAR(3));

--		INSERT INTO Sitting VALUES (@g_value, @a_value, 0, @r_value,null);

--		IF @a_index =50
--				SET @room_index = @room_index + 1;

--		SET @seat_number = @seat_number + 1;
--	END;
--GO

--delete from [dbo].[Sitting]
--SELECT * FROM Sitting WHERE idRoom='R3' ORDER BY idRoom, numberSitting

--insert into Customer values ('KH1', N'Đào Ngọc Anh', convert(date, '25/09/2004', 103), '0909989789', 1)
--insert into Customer values ('KH2', N'Lê Thị Thúy Hiền', convert(date, '16/06/2004', 103), '0576453657', 0)
--insert into Customer values ('KH3', N'Trần Phúc Hưng', convert(date, '29/09/2004', 103), '0405768123', 1)
--select * from Customer

insert into TaiKhoan values (1, 'minhdat@gmail.com', '22697101', 'ADMIN')
insert into TaiKhoan values (2, 'conghai@gmail.com', '22692311', 'NHANVIEN')
insert into TaiKhoan values (3, 'minhtuan@gmail.com', '22697621', 'NHANVIEN')
insert into TaiKhoan values (4, 'congtiep@gmail.com', '22692271', 'NHANVIEN')
select * from TaiKhoan

insert into NhanVien values (1, N'Nguyễn Thắng Minh Đạt', convert(date, '13/01/2004', 103), N'NHA TRANG', 'NAM', '0867684603', 1)
insert into NhanVien values (2, N'Trương Công Hải', convert(date, '15/12/2004', 103), N'TP.HCM', 'NAM', '0504756382', 2)
insert into NhanVien values (3, N'Lê Minh Tuấn', convert(date, '18/01/2004', 103), N'Bình Định', 'NAM', '0909867456', 3)
insert into NhanVien values (4, N'Lê Huỳnh Công Tiếp', convert(date, '01/01/2004', 103), N'Bình Định', 'NAM', '0982757203', 4)
select * from NhanVien

insert into Director values ('DD1', 'Christopher Edward Nolan', convert(date, '30/07/1970', 103))
insert into Director values ('DD2', 'Miyazaki Hayao', convert(date, '05/01/1941', 103))
insert into Director values ('DD3', 'Michael Bay', convert(date, '17/02/1965', 103))
insert into Director values ('DD4', N'Trấn Thành', convert(date, '05/02/1987', 103))
insert into Director values ('DD5', N'Leigh Whannell', convert(date, '17/01/1977', 103))
select * from Director

insert into Movie values ('P1', N'Transformer: Chiến Binh Cuối Cùng', 154, GETDATE(), N'Hành động', 'poster_p1', 'DD3')
insert into Movie values ('P2', N'Transformer: Kỷ Nguyên Hủy Diệt', 165, GETDATE(), N'Hành động', 'poster_p2', 'DD3')
insert into Movie values ('P3', N'Oppenheimer', 175, GETDATE(), N'Lịch sử', 'poster_p3', 'DD1')
insert into Movie values ('P4', N'Interstellar', 175, GETDATE(), N'Khoa học viễn tưởng', 'poster_p4', 'DD1')
insert into Movie values ('P5', N'Thiếu niên và chim diệc', 124, GETDATE(), N'Hoạt hình', 'poster_p5', 'DD2')
insert into Movie values ('P6', N'Quỷ quyệt 3', 97, GETDATE(), N'Kinh dị', 'poster_p6', 'DD5')
insert into Movie values ('P7', N'Mai', 131, GETDATE(), N'Gia đình', 'poster_p7', 'DD4')
select * from Movie

select * from Screening a where idScreening in (select idScreening from Screening b where a.idMovie = b.idMovie)
delete from Screening
insert into Screening values ('SC1', convert(datetime, '2024-04-30T09:00:00'), 'P1', 'R1')
insert into Screening values ('SC2', convert(datetime, '2024-04-30T11:30:00'), 'P1', 'R2')
insert into Screening values ('SC3', convert(datetime, '2024-04-30T15:00:00'), 'P1', 'R3')
insert into Screening values ('SC4', convert(datetime, '2024-04-30T17:30:00'), 'P1', 'R4')
insert into Screening values ('SC5', convert(datetime, '2024-04-30T22:00:00'), 'P1', 'R5')

insert into Screening values ('SC6', convert(datetime, '2024-05-01T08:00:00'), 'P1', 'R1')
insert into Screening values ('SC7', convert(datetime, '2024-05-01T10:30:00'), 'P1', 'R2')
insert into Screening values ('SC8', convert(datetime, '2024-05-01T12:00:00'), 'P1', 'R3')
insert into Screening values ('SC9', convert(datetime, '2024-05-01T14:30:00'), 'P1', 'R4')
insert into Screening values ('SC10', convert(datetime, '2024-05-01T18:00:00'), 'P1', 'R5')

insert into Screening values ('SC11', convert(datetime, '2024-05-02T10:00:00'), 'P1', 'R1')
insert into Screening values ('SC12', convert(datetime, '2024-05-02T13:30:00'), 'P1', 'R2')
insert into Screening values ('SC13', convert(datetime, '2024-05-02T16:00:00'), 'P1', 'R3')
insert into Screening values ('SC14', convert(datetime, '2024-05-02T19:30:00'), 'P1', 'R4')
insert into Screening values ('SC15', convert(datetime, '2024-05-02T22:00:00'), 'P1', 'R5')

insert into Screening values ('SC16', convert(datetime, '2024-04-30T09:00:00'), 'P2', 'R2')
insert into Screening values ('SC17', convert(datetime, '2024-04-30T11:30:00'), 'P2', 'R3')
insert into Screening values ('SC18', convert(datetime, '2024-04-30T15:00:00'), 'P2', 'R4')
insert into Screening values ('SC19', convert(datetime, '2024-04-30T17:30:00'), 'P2', 'R5')
insert into Screening values ('SC20', convert(datetime, '2024-04-30T22:00:00'), 'P2', 'R1')

insert into Screening values ('SC21', convert(datetime, '2024-05-01T08:00:00'), 'P2', 'R2')
insert into Screening values ('SC22', convert(datetime, '2024-05-01T10:30:00'), 'P2', 'R3')
insert into Screening values ('SC23', convert(datetime, '2024-05-01T12:00:00'), 'P2', 'R4')
insert into Screening values ('SC24', convert(datetime, '2024-05-01T14:30:00'), 'P2', 'R5')
insert into Screening values ('SC25', convert(datetime, '2024-05-01T18:00:00'), 'P2', 'R1')

insert into Screening values ('SC26', convert(datetime, '2024-05-02T10:00:00'), 'P2', 'R2')
insert into Screening values ('SC27', convert(datetime, '2024-05-02T13:30:00'), 'P2', 'R3')
insert into Screening values ('SC28', convert(datetime, '2024-05-02T16:00:00'), 'P2', 'R4')
insert into Screening values ('SC29', convert(datetime, '2024-05-02T19:30:00'), 'P2', 'R5')
insert into Screening values ('SC30', convert(datetime, '2024-05-02T22:00:00'), 'P2', 'R1')

insert into Screening values ('SC31', convert(datetime, '2024-04-30T09:00:00'), 'P3', 'R3')
insert into Screening values ('SC32', convert(datetime, '2024-04-30T11:30:00'), 'P3', 'R4')
insert into Screening values ('SC33', convert(datetime, '2024-04-30T15:00:00'), 'P3', 'R5')
insert into Screening values ('SC34', convert(datetime, '2024-04-30T17:30:00'), 'P3', 'R1')
insert into Screening values ('SC35', convert(datetime, '2024-04-30T22:00:00'), 'P3', 'R2')

insert into Screening values ('SC36', convert(datetime, '2024-05-01T08:00:00'), 'P3', 'R3')
insert into Screening values ('SC37', convert(datetime, '2024-05-01T10:30:00'), 'P3', 'R4')
insert into Screening values ('SC38', convert(datetime, '2024-05-01T12:00:00'), 'P3', 'R5')
insert into Screening values ('SC39', convert(datetime, '2024-05-01T14:30:00'), 'P3', 'R1')
insert into Screening values ('SC40', convert(datetime, '2024-05-01T18:00:00'), 'P3', 'R2')

insert into Screening values ('SC41', convert(datetime, '2024-05-02T10:00:00'), 'P3', 'R3')
insert into Screening values ('SC42', convert(datetime, '2024-05-02T13:30:00'), 'P3', 'R4')
insert into Screening values ('SC43', convert(datetime, '2024-05-02T16:00:00'), 'P3', 'R5')
insert into Screening values ('SC44', convert(datetime, '2024-05-02T19:30:00'), 'P3', 'R1')
insert into Screening values ('SC45', convert(datetime, '2024-05-02T22:00:00'), 'P3', 'R2')

insert into Screening values ('SC46', convert(datetime, '2024-04-30T09:00:00'), 'P4', 'R4')
insert into Screening values ('SC47', convert(datetime, '2024-04-30T11:30:00'), 'P4', 'R5')
insert into Screening values ('SC48', convert(datetime, '2024-04-30T15:00:00'), 'P4', 'R1')
insert into Screening values ('SC49', convert(datetime, '2024-04-30T17:30:00'), 'P4', 'R2')
insert into Screening values ('SC50', convert(datetime, '2024-04-30T22:00:00'), 'P4', 'R3')

insert into Screening values ('SC51', convert(datetime, '2024-05-01T08:00:00'), 'P4', 'R4')
insert into Screening values ('SC52', convert(datetime, '2024-05-01T10:30:00'), 'P4', 'R5')
insert into Screening values ('SC53', convert(datetime, '2024-05-01T12:00:00'), 'P4', 'R1')
insert into Screening values ('SC54', convert(datetime, '2024-05-01T14:30:00'), 'P4', 'R2')
insert into Screening values ('SC55', convert(datetime, '2024-05-01T18:00:00'), 'P4', 'R3')

insert into Screening values ('SC56', convert(datetime, '2024-05-02T10:00:00'), 'P4', 'R4')
insert into Screening values ('SC57', convert(datetime, '2024-05-02T13:30:00'), 'P4', 'R5')
insert into Screening values ('SC58', convert(datetime, '2024-05-02T16:00:00'), 'P4', 'R1')
insert into Screening values ('SC59', convert(datetime, '2024-05-02T19:30:00'), 'P4', 'R2')
insert into Screening values ('SC60', convert(datetime, '2024-05-02T22:00:00'), 'P4', 'R3')

insert into Screening values ('SC61', convert(datetime, '2024-04-30T09:00:00'), 'P5', 'R5')
insert into Screening values ('SC62', convert(datetime, '2024-04-30T11:30:00'), 'P5', 'R1')
insert into Screening values ('SC63', convert(datetime, '2024-04-30T15:00:00'), 'P5', 'R2')
insert into Screening values ('SC64', convert(datetime, '2024-04-30T17:30:00'), 'P5', 'R3')
insert into Screening values ('SC65', convert(datetime, '2024-04-30T22:00:00'), 'P5', 'R4')

insert into Screening values ('SC66', convert(datetime, '2024-05-01T08:00:00'), 'P5', 'R5')
insert into Screening values ('SC67', convert(datetime, '2024-05-01T10:30:00'), 'P5', 'R1')
insert into Screening values ('SC68', convert(datetime, '2024-05-01T12:00:00'), 'P5', 'R2')
insert into Screening values ('SC69', convert(datetime, '2024-05-01T14:30:00'), 'P5', 'R3')
insert into Screening values ('SC70', convert(datetime, '2024-05-01T18:00:00'), 'P5', 'R4')

insert into Screening values ('SC71', convert(datetime, '2024-05-02T10:00:00'), 'P5', 'R5')
insert into Screening values ('SC72', convert(datetime, '2024-05-02T13:30:00'), 'P5', 'R1')
insert into Screening values ('SC73', convert(datetime, '2024-05-02T16:00:00'), 'P5', 'R2')
insert into Screening values ('SC74', convert(datetime, '2024-05-02T19:30:00'), 'P5', 'R3')
insert into Screening values ('SC75', convert(datetime, '2024-05-02T22:00:00'), 'P5', 'R4')

insert into Screening values ('SC76', convert(datetime, '2024-04-30T09:00:00'), 'P6', 'R6')
insert into Screening values ('SC77', convert(datetime, '2024-04-30T11:30:00'), 'P6', 'R7')
insert into Screening values ('SC78', convert(datetime, '2024-04-30T15:00:00'), 'P6', 'R8')
insert into Screening values ('SC79', convert(datetime, '2024-04-30T20:30:00'), 'P6', 'R1')

insert into Screening values ('SC80', convert(datetime, '2024-05-01T08:00:00'), 'P6', 'R6')
insert into Screening values ('SC81', convert(datetime, '2024-05-01T10:30:00'), 'P6', 'R7')
insert into Screening values ('SC82', convert(datetime, '2024-05-01T12:00:00'), 'P6', 'R8')
insert into Screening values ('SC83', convert(datetime, '2024-05-01T17:30:00'), 'P6', 'R1')

insert into Screening values ('SC84', convert(datetime, '2024-05-02T10:00:00'), 'P7', 'R7')
insert into Screening values ('SC85', convert(datetime, '2024-05-02T13:30:00'), 'P7', 'R8')
insert into Screening values ('SC86', convert(datetime, '2024-05-02T16:00:00'), 'P7', 'R6')
insert into Screening values ('SC87', convert(datetime, '2024-05-02T22:30:00'), 'P7', 'R1')

insert into Screening values ('SC88', convert(datetime, '2024-04-30T09:00:00'), 'P7', 'R7')
insert into Screening values ('SC89', convert(datetime, '2024-04-30T11:30:00'), 'P7', 'R8')
insert into Screening values ('SC90', convert(datetime, '2024-04-30T15:00:00'), 'P7', 'R6')
insert into Screening values ('SC91', convert(datetime, '2024-04-30T23:30:00'), 'P7', 'R2')

insert into Screening values ('SC92', convert(datetime, '2024-05-01T08:00:00'), 'P7', 'R7')
insert into Screening values ('SC93', convert(datetime, '2024-05-01T10:30:00'), 'P7', 'R8')
insert into Screening values ('SC94', convert(datetime, '2024-05-01T12:00:00'), 'P7', 'R6')
insert into Screening values ('SC95', convert(datetime, '2024-05-01T20:30:00'), 'P7', 'R2')

insert into Screening values ('SC96', convert(datetime, '2024-05-02T10:00:00'), 'P7', 'R7')
insert into Screening values ('SC97', convert(datetime, '2024-05-02T13:30:00'), 'P7', 'R8')
insert into Screening values ('SC98', convert(datetime, '2024-05-02T16:00:00'), 'P7', 'R6')
insert into Screening values ('SC99', convert(datetime, '2024-05-02T22:30:00'), 'P7', 'R2')


insert into Bill values ('B1', '2024-04-26', 160000, 'NV1',	'KH1', NULL)
insert into Bill values ('B2', '2024-04-26', 800000, 'NV2',	'KH2', NULL)
insert into Bill values ('B3', '2024-04-26', 800000, 'NV3',	'KH3', NULL)
insert into Bill values ('B4', '2024-04-26', 240000, 'NV1',	'KH1', NULL)
insert into Bill values ('B5', '2024-04-26', 160000, 'NV2',	'KH2', NULL)
insert into Bill values ('B6', '2024-04-26', 320000, 'NV3',	'KH3', NULL)
insert into Bill values ('B7', '2024-04-26', 160000, 'NV2',	'KH1', NULL)
insert into Bill values ('B8', '2024-04-26', 400000, 'NV3',	'KH3', NULL)


create table ScreeningSitting
(
	idScreening nvarchar(100),
	dateStart datetime,
	idMovie nvarchar(100) foreign key references Movie (idMovie),
	idRoom  nvarchar(100) foreign key references Room (idRoom) ,
	idSitting nvarchar(100),
	numberSitting nvarchar(255),
	state int,
	idTicket nvarchar(100) references Ticket (idTicket)
	primary key(idScreening, idSitting)
)

--Fix
sp_helpconstraint ScreeningSitting

--Xóa foreign key idTicket cũ
alter table ScreeningSitting
	DROP constraint FK__Screening__idScr__0A9D95DB

alter table ScreeningSitting
	Add FOREIGN KEY (idTicket) REFERENCES Ticket (idTicket) on delete cascade on update cascade


delete 
from ScreeningSitting
where idMovie= (
	select idMovie from Movie m join Director d on m.idDirector=d.idDirector
	where d.idDirector=?
)
--Fix

insert into ScreeningSitting (idScreening, dateStart, idMovie, idRoom, idSitting, numberSitting, state, idTicket)
select idScreening, dateStart, idMovie, sc.idRoom, idSitting, numberSitting, state, idTicket
from Screening sc join Sitting s on sc.idRoom=s.idRoom 
