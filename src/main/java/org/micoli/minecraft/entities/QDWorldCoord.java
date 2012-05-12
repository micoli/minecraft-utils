package org.micoli.minecraft.entities;

import org.bukkit.Location;

public class QDWorldCoord {
	String world="";
	int x=0;
	int y=0;
	int z=0;
	
	public QDWorldCoord() {
	}
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param world
	 */
	public QDWorldCoord(String world,int x, int y, int z) {
		super();
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean equals(QDWorldCoord other){
		return (other.x==x && other.y==y && other.z==z && other.world.equals(world));
	}
	
	public QDWorldCoord(Location location) {
		super();
		this.setWorld(location.getWorld().getName());
		this.setXYZ(location.getX(),location.getY(),location.getZ());
	}
	
	public void setXYZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setXYZ(double x, double y, double z) {
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
	}

	public String toString(){
		return String.format("{world:%s,x:%d,y:%d,z:%d}", world,x,y,z);
	}
	
	/**
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}
	/**
	 * @param world the world to set
	 */
	public void setWorld(String world) {
		this.world = world;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}
}
