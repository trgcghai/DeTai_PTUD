package entity;

public class HoSo {
	private String maHS;
	private String moTa;
	private String trangThai;
	private UngVien ungVien;
	private TinTuyenDung tinTuyenDung;
	private NhanVien nhanVien;

	public String getMaHS() {
		return maHS;
	}

	public void setMaHS(String maHS) {
		this.maHS = maHS;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public UngVien getUngVien() {
		return ungVien;
	}

	public void setUngVien(UngVien ungVien) {
		this.ungVien = ungVien;
	}

	public TinTuyenDung getTinTuyenDung() {
		return tinTuyenDung;
	}

	public void setTinTuyenDung(TinTuyenDung tinTuyenDung) {
		this.tinTuyenDung = tinTuyenDung;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maHS == null) ? 0 : maHS.hashCode());
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
		HoSo other = (HoSo) obj;
		if (maHS == null) {
			if (other.maHS != null)
				return false;
		} else if (!maHS.equals(other.maHS))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HoSo [maHS=" + maHS + ", moTa=" + moTa + ", trangThai=" + trangThai + ", ungVien=" + ungVien
				+ ", tinTuyenDung=" + tinTuyenDung + ", nhanVien=" + nhanVien + "]";
	}

	public HoSo() {
		super();
	}

	public HoSo(String maHS, String moTa, String trangThai, UngVien ungVien, TinTuyenDung tinTuyenDung,
			NhanVien nhanVien) {
		super();
		this.maHS = maHS;
		this.moTa = moTa;
		this.trangThai = trangThai;
		this.ungVien = ungVien;
		this.tinTuyenDung = tinTuyenDung;
		this.nhanVien = nhanVien;
	}

}
