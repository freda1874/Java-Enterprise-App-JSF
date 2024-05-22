/*****************************************************************
 * File:  InventoryDao.java
 * Course materials CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public interface InventoryDao {

	// add more to this interface

	// Create new inventory record
	public int createInventory(Inventory inv);
	// R - read all inventory for the specified region
	public List<Inventory> readAllInventoryForRegion(String region);

	// R - read a specific inventory
	public Inventory readInventoryById(int id);
	
	// Update
	public int updateInventory(Inventory inv);

	// Delete
	public int DeleteInventory(int id);

}