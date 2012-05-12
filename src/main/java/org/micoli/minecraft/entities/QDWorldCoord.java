package org.micoli.minecraft.entities;

import org.bukkit.Location;

/**
 * The Class QDWorldCoord.
 */
public class QDWorldCoord {
	
	/** The world. */
	String world="";
	
	/** The x. */
	int x=0;
	
	/** The y. */
	int y=0;
	
	/** The z. */
	int z=0;
	
	/**
	 * Instantiates a new qD world coord.
	 */
	public QDWorldCoord() {
	}
	
	/**
	 * Instantiates a new qD world coord.
	 *
	 * @param world the world
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public QDWorldCoord(String world,int x, int y, int z) {
		super();
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Equals.
	 *
	 * @param other the other
	 * @return true, if successful
	 */
	public boolean equals(QDWorldCoord other){
		return (other.x==x && other.y==y && other.z==z && other.world.equals(world));
	}
	
	/**
	 * Instantiates a new qD world coord.
	 *
	 * @param location the location
	 */
	public QDWorldCoord(Location location) {
		super();
		this.setWorld(location.getWorld().getName());
		this.setXYZ(location.getX(),location.getY(),location.getZ());
	}
	
	/**
	 * Sets the xyz.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void setXYZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Sets the xyz.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void setXYZ(double x, double y, double z) {
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return String.format("{world:%s,x:%d,y:%d,z:%d}", world,x,y,z);
	}
	
	/**
	 * Gets the world.
	 *
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}
	
	/**
	 * Sets the world.
	 *
	 * @param world the world to set
	 */
	public void setWorld(String world) {
		this.world = world;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	
	/**
	 * Sets the z.
	 *
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}
}
