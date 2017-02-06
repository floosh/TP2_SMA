package fr.disp.polytech.sma.tp1.sma.environment;

import java.io.Serializable;
import java.util.Date;

import javax.naming.directory.Attribute;
import javax.vecmath.Point2d;

import org.arakhne.tinyMAS.core.AgentIdentifier;
/**
 * Classe Info : représente les messages échangés entre agents
 */
public class Info implements javax.print.attribute.Attribute {

	private String info;
	private long time;
	private int idInfo;
	private Point2d locationInfo;
	private Point2d locationInfo2;
	private AgentIdentifier agentId; //sender
	private AgentIdentifier targetAgentInfo;
	
	/**
	 * Constructeur du message avec son contenu
	 * @param info
	 */
	public Info(String info)
	{
		this.info = info;
	}
	
	public Class<? extends javax.print.attribute.Attribute> getCategory()
	{
		return null;
	}
	
	/**
	 * Set la position de l'info
	 * @param locationInfo
	 */
	public void setLocationInfo(Point2d locationInfo) {
		this.locationInfo = locationInfo;
	}

	/**
	 * Renvoi la position de l'info
	 * @return
	 */
	public Point2d getLocationInfo() {
		return locationInfo;
	}

	/**
	 * Set l'identifiant de l'info
	 * @param idInfo
	 */
	public void setIdInfo(int idInfo) {
		this.idInfo = idInfo;
	}

	/**
	 * Renvoi l'identifiant de l'info
	 * @return
	 */
	public int getIdInfo() {
		return idInfo;
	}
	
	/**
	 * Renvoi le nom de l'info
	 */
	 
	public String getName() {
		return this.info;
	}

	/**
	 * Set l'identifiant de l'agent qui envoi l'info
	 * @param agentId
	 */
	public void setAgentId(AgentIdentifier agentId) {
		this.agentId = agentId;
	}
	
	/**
	 * Renvoi l'identifiant de l'envoyeur
	 * @return
	 */
	public AgentIdentifier getAgentId() {
		return agentId;
	}
	
	/**
	 * Set l'heure à laquelle l'info a été envoyée
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Renvoi l'heure à laquelle l'info a été envoyée
	 * @return
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Set l'agent auquel il faut envoyer l'info
	 * @param agentId
	 */
	public void setTargetAgentInfo(AgentIdentifier agentId) {
		this.targetAgentInfo = agentId;
	}

	/**
	 * Renvoi l'id de l'agent auquel va etre envoyé l'info
	 * @return
	 */
	public AgentIdentifier getTargetAgentInfo() {
		return targetAgentInfo;
	}

	public void setLocationInfo2(Point2d locationInfo2) {
		this.locationInfo2 = locationInfo2;
	}

	public Point2d getLocationInfo2() {
		return locationInfo2;
	}
	
}
