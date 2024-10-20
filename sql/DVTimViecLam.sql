===================================================================================================================
use master

CREATE DATABASE DVTimViecLam;
USE DVTimViecLam;

CREATE TABLE NhanVien (
    MaNV NVARCHAR(10) PRIMARY KEY,
    TenNV NVARCHAR(100),
    NgaySinh DATE,
    DiaChi NVARCHAR(255),
    GioiTinh NVARCHAR(10),
    SoDienThoai NVARCHAR(20),
    NgayVaoLam DATE
);

CREATE TABLE NhaTuyenDung (
    MaNTD NVARCHAR(10) PRIMARY KEY,
    TenNTD NVARCHAR(100),
    Email NVARCHAR(100),
    Logo NVARCHAR(255),
    DiaChi NVARCHAR(255),
    SoDienThoai NVARCHAR(20)
);

CREATE TABLE TinTuyenDung (
    MaTTD NVARCHAR(10) PRIMARY KEY,
    TieuDe NVARCHAR(255),
    MoTa NVARCHAR(MAX),
    NgayDangTin DATE,
    NgayHetHan DATE,
    TrinhDo NVARCHAR(50),
    SoLuong INT,
    Luong DECIMAL(10, 2),
    NganhNghe NVARCHAR(100),
    TrangThai BIT, -- 0 là False, 1 là True
    HinhThuc NVARCHAR(50),
    MaNTD NVARCHAR(10),
    FOREIGN KEY (MaNTD) REFERENCES NhaTuyenDung(MaNTD)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE TaiKhoan (
    MaTK NVARCHAR(10) PRIMARY KEY,
    Email NVARCHAR(100),
    MatKhau NVARCHAR(255),
    VaiTro NVARCHAR(50),
    MaNV NVARCHAR(10),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE UngVien (
    MaUV NVARCHAR(10) PRIMARY KEY,
    TenUV NVARCHAR(100),
    Email NVARCHAR(100),
    NgaySinh DATE,
    DiaChi NVARCHAR(255),
    GioiTinh NVARCHAR(10),
    SoDienThoai NVARCHAR(20)
);

CREATE TABLE HoSo (
    MaHS NVARCHAR(10) PRIMARY KEY,
    MoTa NVARCHAR(MAX),
    TrangThai NVARCHAR(50),
    MaTTD NVARCHAR(10),
    MaUV NVARCHAR(10),
    MaNV NVARCHAR(10),
    FOREIGN KEY (MaTTD) REFERENCES TinTuyenDung(MaTTD),
    FOREIGN KEY (MaUV) REFERENCES UngVien(MaUV),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE HopDong (
    MaHD NVARCHAR(10) PRIMARY KEY,
    PhiDichVu DECIMAL(10, 2),
    ThoiGian DATE,
    MaTTD NVARCHAR(10),
    MaUV NVARCHAR(10),
    MaNV NVARCHAR(10),
    FOREIGN KEY (MaTTD) REFERENCES TinTuyenDung(MaTTD),
    FOREIGN KEY (MaUV) REFERENCES UngVien(MaUV),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);


--Dữ liệu:
INSERT INTO NhanVien (MaNV, TenNV, NgaySinh, DiaChi, GioiTinh, SoDienThoai, NgayVaoLam)
VALUES 
    ('NV01', N'Nguyễn Thắng Minh Đạt', convert(date, '13/01/2004', 103), N'NHA TRANG', 'Nam', '0123456789', convert(date, '01/01/2023', 103)),
    ('NV02', N'Trương Công Hải', convert(date, '15/12/2004', 103), N'TP HỒ CHÍ MINH', 'Nam', '0123456789', convert(date, '01/01/2020', 103));


INSERT INTO NhaTuyenDung (MaNTD, TenNTD, Email, Logo, DiaChi, SoDienThoai)
VALUES 
    ('NTD01', N'Công ty dược phẩm Đại Hưng', 'daihung@daihung.com', 'daihung.jpg', N'319-A12 Lý Thường Kiệt, Phường 15, Quận 11, Hồ Chí Minh', '098 7654 321'),
    ('NTD02', N'Công ty TNHH Bosch Global Software Technologies', 'bosch@bosch.com', 'bosch.jpg', N'4364 Đ. Cộng Hòa, Phường 12, Tân Bình, Hồ Chí Minh', '028 6686 6244'),
    ('NTD03', N'Công ty EM and AI', 'emai@emai.com', 'em&ai.jpg', N'123 ABC Street, Quận 1, Hồ Chí Minh', '090 1234 5678'),
    ('NTD04', N'Công ty Aletech Technology Solutions', 'aletech@aletech.com', 'cles.jpg', N'789 Đ. Lê Văn Sỹ, Phường 13, Quận 3, Hồ Chí Minh', '098 7654 321'),
    ('NTD05', N'Công Ty Tnhh Lg Electronics Việt Nam', 'lg@lg.com', 'lg.jpg', N'123 Đ. Điện Biên Phủ, Phường 17, Bình Thạnh, Hồ Chí Minh', '028 7777 8888'),
    ('NTD06', N'Công ty Viễn thông Viettel', 'viettel@viettel.com', 'vittel.jpg', N'25 Đ. Đinh Tiên Hoàng, Phường Đa Kao, Quận 1, Hồ Chí Minh', '090 6789 012'),
    ('NTD07', N'Công Ty TNHH Glodival', 'glodival@glodival.com', 'g.jpg', N'12 Đ. Hoàng Hoa Thám, Phường 13, Bình Thạnh, Hồ Chí Minh', '091 2345 678'),
    ('NTD08', N'Ngân hàng Thương mại cổ phần Việt Nam Thịnh Vượng (VPBank)', 'vpbank@vpbank.com', 'vpbank.jpg', N'202 Đ. Lê Lợi, Quận 1, Hồ Chí Minh', '028 9999 8888'),
    ('NTD09', N'Công Ty cổ phần cơ điện Tomeco', 'tomeco@tomeco.com', 'tomeco.jpg', N'58 Đ. Nam Kỳ Khởi Nghĩa, Phường 8, Quận 3, Hồ Chí Minh', '028 3333 4444'),
    ('NTD10', N'Công ty TNHH Phần mềm FPT', 'fpt@fpt.com', 'fpt.jpg', N'21 Đ. Trường Sơn, Tân Bình, Hồ Chí Minh', '028 6666 7777'),
    ('NTD11', N'Công ty tài chính đến từ Hàn Quốc Shinhan Finance', 'shinhan@shinhan.com', 'shinhans.jpg', N'123 Đ. Nguyễn Văn Trỗi, Phường 10, Phú Nhuận, Hồ Chí Minh', '028 2222 1111'),
    ('NTD12', N'Công ty TNHH PASONA Tech Việt Nam', 'pasona@pasona.com', 'pasona.jpg', N'10 Đ. Phan Đăng Lưu, Phường 6, Bình Thạnh, Hồ Chí Minh', '028 5555 6666'),
    ('NTD13', N'Công ty cổ phần kỹ nghệ BANICO', 'banico@banico.com', 'banico.jpg', N'8 Đ. Đinh Bộ Lĩnh, Phường 26, Bình Thạnh, Hồ Chí Minh', '028 4444 5555'),
    ('NTD14', N'Công ty cổ phần STRINGEE', 'stringee@stringee.com', 'stringee.jpg', N'54 Đ. Võ Thị Sáu, Phường 8, Quận 3, Hồ Chí Minh', '028 6666 4444'),
    ('NTD15', N'Công ty TNHH NGen', 'ngen@ngen.com', 'ngen.jpg', N'34 Đ. Nguyễn Thái Sơn, Gò Vấp, Hồ Chí Minh', '028 3333 2222'),
    ('NTD16', N'Công Ty TNHH Phát Triển Giải Pháp & CNTT Diệp Anh', 'diepanh@diepanh.com', 'diepanh.jpg', N'90 Đ. Trần Quốc Toản, Quận 3, Hồ Chí Minh', '090 1234 5678'),
    ('NTD17', N'Công ty Cổ phần Công nghệ Tin học và Dịch vụ Goline', 'goline@goline.com', 'goline.jpg', N'26 Đ. Bạch Đằng, Phường 15, Bình Thạnh, Hồ Chí Minh', '012388883');


INSERT INTO TinTuyenDung (MaTTD, TieuDe, MoTa, NgayDangTin, NgayHetHan, TrinhDo, SoLuong, Luong, NganhNghe, TrangThai, HinhThuc, MaNTD)
VALUES 
    ('TTD01', N'Tuyển lập trình viên backend', N'Phát triển hệ thống backend cho ứng dụng.', CONVERT(DATE, '01/01/2024', 103), CONVERT(DATE, '01/04/2024', 103), N'Đại học', 5, 120000.00, N'IT', 1, N'Full-time', 'NTD01'),
    ('TTD02', N'Tuyển lập trình viên mobile', N'Phát triển ứng dụng di động cho iOS và Android.', CONVERT(DATE, '02/01/2024', 103), CONVERT(DATE, '01/05/2024', 103), N'Đại học', 4, 130000.00, N'IT', 1, N'Online', 'NTD02'),
    ('TTD03', N'Tuyển kỹ sư DevOps', N'Quản lý hạ tầng và triển khai ứng dụng.', CONVERT(DATE, '03/01/2024', 103), CONVERT(DATE, '01/06/2024', 103), N'Đại học', 3, 1500.00, N'IT', 1, N'Full-time', 'NTD01'),
    ('TTD04', N'Tuyển chuyên viên an ninh mạng', N'Đảm bảo an toàn thông tin cho hệ thống.', CONVERT(DATE, '04/01/2024', 103), CONVERT(DATE, '01/07/2024', 103), N'Đại học', 2, 160000.00, N'IT', 1, N'Part-time', 'NTD02'),
    ('TTD05', N'Tuyển kỹ sư dữ liệu', N'Phân tích và quản lý dữ liệu lớn.', CONVERT(DATE, '05/01/2024', 103), CONVERT(DATE, '01/08/2024', 103), N'Đại học', 3, 1400000.00, N'IT', 1, N'Online', 'NTD03'),
    ('TTD06', N'Tuyển phát triển ứng dụng web', N'Xây dựng và phát triển ứng dụng web.', CONVERT(DATE, '06/01/2024', 103), CONVERT(DATE, '01/09/2024', 103), N'Đại học', 5, 110000.00, N'IT', 1, N'Full-time', 'NTD04'),
    ('TTD07', N'Tuyển kiểm thử phần mềm', N'Kiểm tra và đánh giá chất lượng phần mềm.', CONVERT(DATE, '07/01/2024', 103), CONVERT(DATE, '01/10/2024', 103), N'Cao đẳng', 4, 90000.00, N'IT', 1, N'Part-time', 'NTD05'),
    ('TTD08', N'Tuyển kỹ sư phần mềm', N'Phát triển phần mềm theo yêu cầu.', CONVERT(DATE, '08/01/2024', 103), CONVERT(DATE, '01/11/2024', 103), N'Đại học', 2, 1700.00, N'IT', 1, N'Online', 'NTD06'),
    ('TTD09', N'Tuyển nhân viên hỗ trợ kỹ thuật', N'Hỗ trợ khách hàng về kỹ thuật.', CONVERT(DATE, '09/01/2024', 103), CONVERT(DATE, '01/12/2024', 103), N'Cao đẳng', 3, 80000.00, N'IT', 1, N'Full-time', 'NTD07'),
    ('TTD10', N'Tuyển nhân viên văn phòng', N'Thực hiện các công việc hành chính.', CONVERT(DATE, '10/01/2024', 103), CONVERT(DATE, '01/05/2024', 103), N'THPT', 4, 70000.00, N'Hành chính', 1, N'Full-time', 'NTD08'),
    ('TTD11', N'Tuyển nhân viên bán hàng', N'Bán hàng và tư vấn khách hàng.', CONVERT(DATE, '11/01/2024', 103), CONVERT(DATE, '01/06/2024', 103), N'THPT', 3, 60000.00, N'Bán lẻ', 1, N'Part-time', 'NTD09'),
    ('TTD12', N'Tuyển nhân viên phục vụ', N'Phục vụ khách hàng tại nhà hàng.', CONVERT(DATE, '12/01/2024', 103), CONVERT(DATE, '01/07/2024', 103), N'THPT', 5, 50000.00, N'Dịch vụ', 1, N'Offline', 'NTD10'),
    ('TTD13', N'Tuyển nhân viên marketing', N'Xây dựng chiến lược marketing.', CONVERT(DATE, '13/01/2024', 103), CONVERT(DATE, '01/08/2024', 103), N'Cao đẳng', 2, 100000.00, N'Marketing', 1, N'Full-time', 'NTD11'),
    ('TTD14', N'Tuyển nhân viên kế toán', N'Quản lý sổ sách và báo cáo tài chính.', CONVERT(DATE, '14/01/2024', 103), CONVERT(DATE, '01/09/2024', 103), N'Đại học', 1, 120000.00, N'Kế toán', 1, N'Part-time', 'NTD12'),
    ('TTD15', N'Tuyển nhân viên thiết kế đồ họa', N'Thiết kế đồ họa cho các dự án.', CONVERT(DATE, '15/01/2024', 103), CONVERT(DATE, '01/10/2024', 103), N'Cao đẳng', 2, 110000.00, N'Thiết kế', 1, N'Online', 'NTD13');

INSERT INTO TaiKhoan (MaTK, Email, MatKhau, VaiTro, MaNV)
VALUES 
    ('TK01', 'MinhDat@gmail.com', '123', 'Admin', 'NV01'),
    ('TK02', 'CongHai@gmail.com', '456', 'NhanVien', 'NV02');

INSERT INTO UngVien (MaUV, TenUV, Email, NgaySinh, DiaChi, GioiTinh, SoDienThoai)
VALUES 
    ('UV01', N'Lê Huỳnh Công Tiếp', 'le.c@gmail.com', convert(date, '01/01/2004', 103), N'Bình Định', 'Nam', '0912345678'),
    ('UV02', N'Lê Minh Tuấn', 'minhTuan@gmail.com', convert(date, '18/01/2004', 103), N'Gia Lai', 'Nam', '0934567890');

INSERT INTO HoSo (MaHS, MoTa, TrangThai, MaTTD, MaUV, MaNV)
VALUES 
    ('HS07', N'- Mục tiêu nghề nghiệp: Mong muốn trở thành chuyên gia trong lĩnh vực Công nghệ thông tin, đặc biệt trong mảng phát triển phần mềm. Định hướng làm việc trong môi trường chuyên nghiệp, có cơ hội học hỏi và thăng tiến.\n'
    +N'- Trình độ: Đại học\n'
    +N'- Chuyên ngành: Công nghệ thông tin\n'
    +N'- Kinh nghiệm làm việc: Công ty ABC – Lập trình viên, Phát triển và bảo trì các hệ thống quản lý nội bộ bằng Java và Spring Framework.\n'
    +N'- Kỹ năng chuyên môn:\n\tThành thạo các ngôn ngữ lập trình: Java, Python, SQL.\n\tKinh nghiệm làm việc với Git và Docker.\n\tKhả năng phân tích và giải quyết vấn đề hiệu quả.\n'
    +N'- Ngoại ngữ Tiếng Anh: Giao tiếp tốt (TOEIC 850).', N'Chờ', 'TTD01', 'UV01', 'NV01'),
    ('HS08', N'- Mong muốn trở thành một chuyên gia trong lĩnh vực Marketing, đặc biệt tập trung vào Digital Marketing và Phân tích dữ liệu khách hàng. Hướng đến vị trí Quản lý Marketing, góp phần xây dựng chiến lược hiệu quả và gia tăng nhận diện thương hiệu.\n'
    +N'- Trình độ: Đại học\n'
    +N'- Chuyên ngành: Marketing\n'
    +N'- Kinh nghiệm làm việc: Công ty ABC – Chuyên viên Digital Marketing, Lên kế hoạch và triển khai các chiến dịch quảng cáo trên Facebook, Google Ads. Theo dõi và tối ưu hiệu quả chiến dịch dựa trên các chỉ số KPI.\n'
    +N'- Kỹ năng chuyên môn:\n\tThành thạo các công cụ quảng cáo: Facebook Ads, Google Analytics, SEO.\n\tKỹ năng sáng tạo nội dung và quản lý kênh truyền thông xã hội.\n\tKinh nghiệm phân tích dữ liệu và tối ưu hóa chi phí quảng cáo.\n'
    +N'- Ngoại ngữ: Giao tiếp tốt (IELTS 7.0).', N'Đã Duyệt', 'TTD02', 'UV02', 'NV02');

INSERT INTO HopDong (MaHD, PhiDichVu, ThoiGian, MaTTD, MaUV, MaNV)
VALUES 
    ('HD01', 500.00, CONVERT(DATE, '05/01/2024', 103), 'TTD01', 'UV01', 'NV01'),
    ('HD02', 800.00, CONVERT(DATE, '06/01/2024', 103), 'TTD02', 'UV02', 'NV02');
================================================================================================================================

Select * from TaiKhoan where MaNV='NV01'

Select * from NhanVien where TenNV LIKE N'%Đ%' AND SoDienThoai LIKE '%0%'

Select * from 
NhanVien n join TaiKhoan t on n.MaNV=t.MaNV 
where MaTK= 'TK01'

delete from TaiKhoan where MaTK='TK03'

Select * from UngVien where TenUV LIKE N'%L%'

Select * from HoSo where TrangThai LIKE N'%Chưa nộp%'

delete from HoSo where MaHS='HS03'

select * from NhaTuyenDung

select * from TinTuyenDung where MaTTD = 'TTD01'

Select * from HoSo where TrangThai LIKE N'Chưa nộp'

Select * 
from HoSo h join UngVien u
on h.MaUV = u.MaUV
where TrangThai LIKE N'Chưa nộp' AND u.TenUV LIKE N'%Minh%'

select * from NhaTuyenDung

select * from 
TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD
where TieuDe Like N'%kỹ sư%' AND Luong=1000 AND TenNTD LIKE '%Shinhan%' 

select * from TinTuyenDung 
where TrinhDo LIKE N'%Cao đẳng%' AND NganhNghe LIKE N'%IT%'
