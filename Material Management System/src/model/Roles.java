package model;

public class Roles {

	int id;
	int create;
	int update;
	int delete;
	int view;
	int rolId;
	int auth;
	
	
	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	//fk
	int pageId;
	
	String pageIdNameHolder;
	String roleNameHolder;
	
	
	

	public String getRoleNameHolder() {
		return roleNameHolder;
	}

	public void setRoleNameHolder(String roleNameHolder) {
		this.roleNameHolder = roleNameHolder;
	}

	public int getRolId() {
		return rolId;
	}

	public void setRolId(int rolId) {
		this.rolId = rolId;
	}

	public String getPageIdNameHolder() {
		return pageIdNameHolder;
	}

	public void setPageIdNameHolder(String pageIdNameHolder) {
		this.pageIdNameHolder = pageIdNameHolder;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCreate() {
		return create;
	}

	public void setCreate(int create) {
		this.create = create;
	}

	public int getUpdate() {
		return update;
	}

	public void setUpdate(int update) {
		this.update = update;
	}

	public int getDelete() {
		return delete;
	}

	public void setDelete(int delete) {
		this.delete = delete;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	
	
}
