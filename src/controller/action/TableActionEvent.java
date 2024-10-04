package controller.action;

public interface TableActionEvent {
	public void onUpdate(int row);
    public void onDelete(int row);
    public void onViewHoSo(int row);
    public void onCreateHoSo(int row);
    public void onCreateTaiKhoan(int row);
}
