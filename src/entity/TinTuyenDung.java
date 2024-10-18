package entity;

import java.time.LocalDate;

import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrinhDo;

public class TinTuyenDung {
	private String maTTD;
	private String tieuDe;
	private String moTa;
	private LocalDate ngayDangTin;
	private LocalDate ngayHetHan;
	private TrinhDo trinhDo;
	private int soLuong;
	private double luong;
	private NganhNghe nganhNghe;
	private HinhThucLamViec hinhThuc;
	private boolean trangThai;
	private NhaTuyenDung nhaTuyenDung;

	public String getMaTTD() {
		return maTTD;
	}

	public void setMaTTD(String maTTD) {
		this.maTTD = maTTD;
	}

	public String getTieuDe() {
		return tieuDe;
	}

	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public LocalDate getNgayDangTin() {
		return ngayDangTin;
	}

	public void setNgayDangTin(LocalDate ngayDangTin) {
		this.ngayDangTin = ngayDangTin;
	}

	public LocalDate getNgayHetHan() {
		return ngayHetHan;
	}

	public void setNgayHetHan(LocalDate ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}

	public TrinhDo getTrinhDo() {
		return trinhDo;
	}

	public void setTrinhDo(TrinhDo trinhDo) {
		this.trinhDo = trinhDo;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getLuong() {
		return luong;
	}

	public void setLuong(double luong) {
		this.luong = luong;
	}

	public NganhNghe getNganhNghe() {
		return nganhNghe;
	}

	public void setNganhNghe(NganhNghe nganhNghe) {
		this.nganhNghe = nganhNghe;
	}

	public HinhThucLamViec getHinhThuc() {
		return hinhThuc;
	}

	public void setHinhThuc(HinhThucLamViec hinhThuc) {
		this.hinhThuc = hinhThuc;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public NhaTuyenDung getNhaTuyenDung() {
		return nhaTuyenDung;
	}

	public void setNhaTuyenDung(NhaTuyenDung nhaTuyenDung) {
		this.nhaTuyenDung = nhaTuyenDung;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maTTD == null) ? 0 : maTTD.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TinTuyenDung other = (TinTuyenDung) obj;
		if (maTTD == null) {
			if (other.maTTD != null)
				return false;
		} else if (!maTTD.equals(other.maTTD))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TinTuyenDung [maTTD=" + maTTD + ", tieuDe=" + tieuDe + ", moTa=" + moTa + ", ngayDangTin=" + ngayDangTin
				+ ", ngayHetHan=" + ngayHetHan + ", trinhDo=" + trinhDo + ", soLuong=" + soLuong + ", luong=" + luong
				+ ", nganhNghe=" + nganhNghe + ", hinhThuc=" + hinhThuc + ", trangThai=" + trangThai + ", nhaTuyenDung="
				+ nhaTuyenDung + ", congViecMaCV=" + "]";
	}

	public TinTuyenDung(String maTTD, String tieuDe, String moTa, LocalDate ngayDangTin, LocalDate ngayHetHan,
			TrinhDo trinhDo, int soLuong, double luong, NganhNghe nganhNghe, HinhThucLamViec hinhThuc, boolean trangThai,
			NhaTuyenDung nhaTuyenDung) {
		super();
		this.maTTD = maTTD;
		this.tieuDe = tieuDe;
		this.moTa = moTa;
		this.ngayDangTin = ngayDangTin;
		this.ngayHetHan = ngayHetHan;
		this.trinhDo = trinhDo;
		this.soLuong = soLuong;
		this.luong = luong;
		this.nganhNghe = nganhNghe;
		this.hinhThuc = hinhThuc;
		this.trangThai = trangThai;
		this.nhaTuyenDung = nhaTuyenDung;
	}

	public TinTuyenDung() {
		super();
	}

	public TinTuyenDung(String maTTD) {
		super();
		this.maTTD = maTTD;
	}

}
