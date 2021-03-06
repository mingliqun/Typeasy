package me.llss.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.llss.dao.MetasDAO;
import me.llss.utils.DBConnection;
import me.llss.vo.MetasVO;

/**
 * Metas 数据库操作类
 * 
 * @author Acris
 * @version 2.0 2013/09/17 11:34
 * 
 */
public class MetasDAOImpl implements MetasDAO {

	/**
	 * 按项目编号查找信息
	 * 
	 * @param mid
	 * @return
	 */
	public MetasVO findByMid(int mid) {
		Connection conn = DBConnection.getConnection();
		MetasVO meta = new MetasVO();
		String sql = "SELECT * FROM `metas` WHERE `mid`=? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				meta.setMid(rs.getInt("mid"));
				meta.setName(rs.getString("name"));
				meta.setSlug(rs.getString("slug"));
				meta.setType(rs.getString("type"));
				meta.setDescription(rs.getString("description"));
				meta.setCount(rs.getInt("count"));
				meta.setOrder(rs.getInt("order"));
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return meta;
	}

	/**
	 * 按项目名称查找信息
	 * 
	 * @param name
	 * @return
	 */
	public MetasVO findByName(String name) {
		Connection conn = DBConnection.getConnection();
		MetasVO meta = new MetasVO();
		String sql = "SELECT * FROM `metas` WHERE `name`=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				meta.setMid(rs.getInt("mid"));
				meta.setName(rs.getString("name"));
				meta.setSlug(rs.getString("slug"));
				meta.setType(rs.getString("type"));
				meta.setDescription(rs.getString("description"));
				meta.setCount(rs.getInt("count"));
				meta.setOrder(rs.getInt("order"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return meta;

	}

	/**
	 * 插入项目信息
	 * 
	 * @param met
	 */

	public void add(MetasVO meta) {
		Connection conn = DBConnection.getConnection();
		String sql = "INSERT INTO `metas`(`name`,`slug`,`type`,`description`) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, meta.getName());
			ps.setString(2, meta.getSlug());
			ps.setString(3, meta.getType());
			ps.setString(4, meta.getDescription());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 按项目编号删除项目信息
	 * 
	 * @param mid
	 */
	public void del(int mid) {
		Connection conn = DBConnection.getConnection();
		String sql = "DELETE FROM `metas` WHERE `mid`=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 修改项目信息
	 * 
	 * @param met
	 */
	public void edit(MetasVO meta) {
		Connection conn = DBConnection.getConnection();
		String sql = "UPDATE `metas` SET `name`=?,`slug`=?,`type`=?,`description`=? WHERE `mid`=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(5, meta.getMid());
			ps.setString(1, meta.getName());
			ps.setString(2, meta.getSlug());
			ps.setString(3, meta.getType());
			ps.setString(4, meta.getDescription());
			ps.executeUpdate();
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查找所有的项目信息
	 * 
	 * @return
	 */
	public List<MetasVO> find() {
		List<MetasVO> metas = new ArrayList<MetasVO>();
		Connection conn = DBConnection.getConnection();
		MetasVO meta = null;
		String sql = "SELECT * FROM `metas`";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				meta = new MetasVO();
				meta.setMid(rs.getInt("mid"));
				meta.setName(rs.getString("name"));
				meta.setSlug(rs.getString("slug"));
				meta.setType(rs.getString("type"));
				meta.setDescription(rs.getString("description"));
				meta.setCount(rs.getInt("count"));
				meta.setOrder(rs.getInt("order"));
				metas.add(meta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return metas;
	}

	/**
	 * 修改对应的文章数
	 * 
	 * @param mid
	 * @param op
	 */
	public void editCount(int mid, String op) {
		Connection conn = DBConnection.getConnection();
		String sql = "";
		if (op == null)
			op = "";
		if (op.equals("+"))
			sql = "UPDATE `metas` SET `count`=`count`+1 WHERE `mid`=?";
		else if (op.equals("-"))
			sql = "UPDATE `metas` SET `count`=`count`-1 WHERE `mid`=?";
		else
			sql = "UPDATE `metas` SET `count`=`count` WHERE `mid`=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
			ps.executeUpdate();
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
