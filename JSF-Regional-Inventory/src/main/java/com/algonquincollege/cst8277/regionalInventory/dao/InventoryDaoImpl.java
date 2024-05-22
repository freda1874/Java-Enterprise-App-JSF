/*****************************************************************
 * File:  InventoryDaoImpl.java
 * Course materials CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

@Named
@ApplicationScoped
public class InventoryDaoImpl implements InventoryDao, Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	public static final String GET_ALL_INVENTORY_FOR_REGION_SQL = 
			"SELECT * FROM REGIONAL_INVENTORY WHERE REGION = ?";

	public static final String ADD_INVENTORY_SQL = 
			
	"INSERT INTO REGIONAL_INVENTORY"
	+ "(INV_ID, "
	+ "RETAILER_NAME, "
	+ "CURR_INV_LEVEL, "
	+ "REGION, CREATED, "
	+ "UPDATED) VALUES (?, ?, ?, ?, now(), now());";

	public static final String READ_SPECIFIC_INVENTORY_SQL = 
			"SELECT * FROM REGIONAL_INVENTORY WHERE INV_ID=? ; ";

	public static final String Update_INVENTORY_SQL = 
			"UPDATE REGIONAL_INVENTORY SET RETAILER_NAME=?, CURR_INV_LEVEL=?,REGION=? , UPDATED=now(), VERSION = VERSION + 1 where INV_ID=? ;";

	public static final String DELETE_INVENTORY_SQL = 
			"DELETE FROM REGIONAL_INVENTORY WHERE  INV_ID=? ;";


	@Inject
	protected ServletContext ctx;

	private void logMsg(String msg) {
		ctx.log(msg);
	}

	@Resource(lookup = "java:app/jdbc/regionalInventory")
	protected DataSource regionalInventoryDataSource;

	protected Connection conn;
	protected PreparedStatement getAllPstmt;
	protected PreparedStatement insertPstmt;
	protected PreparedStatement deletePstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement getOnePstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection ...");
			conn = regionalInventoryDataSource.getConnection();
			getAllPstmt = conn.prepareStatement(GET_ALL_INVENTORY_FOR_REGION_SQL);
			// build more statements for rest of C-R-U-D interface
			insertPstmt = conn.prepareStatement(ADD_INVENTORY_SQL, Statement.RETURN_GENERATED_KEYS);
			getOnePstmt = conn.prepareStatement(READ_SPECIFIC_INVENTORY_SQL);
			updatePstmt = conn.prepareStatement(Update_INVENTORY_SQL);
			deletePstmt = conn.prepareStatement(DELETE_INVENTORY_SQL);

		} catch (Exception e) {
			logMsg("something went wrong getting connection: " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection ...");
			getAllPstmt.close();
			// close rest of statements ...
			getOnePstmt.close();
			updatePstmt.close();
			deletePstmt.close();
			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing connection: " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<Inventory> readAllInventoryForRegion(String region) {
		List<Inventory> inventoryList = new ArrayList<>();
		try {
			getAllPstmt.setString(1, region);
			ResultSet rs = getAllPstmt.executeQuery();
			while (rs.next()) {
				Inventory newInventory = new InventoryDTO();
				newInventory.setId(rs.getInt("INV_ID"));
				newInventory.setRetailerName(rs.getString("RETAILER_NAME"));
				newInventory.setRegion(rs.getString("REGION"));
				newInventory.setInventoryLevel(rs.getInt("CURR_INV_LEVEL"));
				inventoryList.add(newInventory);
			}
			try {
				rs.close();
			} catch (SQLException e) {
				logMsg("something went wrong closing resultSet: " + e.getLocalizedMessage());
			}
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		return inventoryList;
	}

	@Override
	public int createInventory(Inventory inv) {
		int rows = 0;
		try {
			insertPstmt.setInt(1, inv.getId());
			insertPstmt.setString(2, inv.getRetailerName());
			insertPstmt.setInt(3, inv.getInventoryLevel());
			insertPstmt.setString(4, inv.getRegion());
			rows = insertPstmt.executeUpdate();
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		return rows;
	}

	@Override
	public Inventory readInventoryById(int id) {
		Inventory inv = new InventoryDTO();
		try {
			getOnePstmt.setInt(1, id);
			ResultSet rs = getOnePstmt.executeQuery();
			while (rs.next()) {
				inv.setId(rs.getInt("INV_ID"));
				inv.setRetailerName(rs.getString("RETAILER_NAME"));
				inv.setRegion(rs.getString("REGION"));
				inv.setInventoryLevel(rs.getInt("CURR_INV_LEVEL"));
			}
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		return inv;
	}
 

	@Override
	public int updateInventory(Inventory inv) {
		int rows = 0;
		try {
			updatePstmt.setString(1, inv.getRetailerName());
			updatePstmt.setInt(2, inv.getInventoryLevel());
			updatePstmt.setString(3, inv.getRegion());
			updatePstmt.setInt(4, inv.getId());
			rows = updatePstmt.executeUpdate();
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		return rows;

	}
	 

	@Override
	public int DeleteInventory(int  id) {
		int rows = 0;
		try {
		deletePstmt.setInt(1,id);
		rows =  deletePstmt.executeUpdate();
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		return rows;
	}

}