package it.polito.tdp.bar.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento> {
	
	public enum TIPO{
		ARRIVO_GRUPPO_CLIENTI,
		USCITA_GRUPPO_CLIENTI
	}
	
	private int indicetavolo;
	private LocalTime time;
	private int num_persone;
	private double durata;
	private float tolleranza;
	private TIPO tipo;
	
	public Evento(LocalTime time, int num_persone, double durata, float tolleranza, TIPO tipo, int indice) {
		this.time = time;
		this.num_persone = num_persone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.tipo = tipo;
		indicetavolo=indice;
	}

	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public int getNum_persone() {
		return num_persone;
	}
	public void setNum_persone(int num_persone) {
		this.num_persone = num_persone;
	}
	public double getDurata() {
		return durata;
	}
	public void setDurata(double durata) {
		this.durata = durata;
	}
	public float getTolleranza() {
		return tolleranza;
	}
	public void setTolleranza(float tolleranza) {
		this.tolleranza = tolleranza;
	}
	public TIPO getTipo() {
		return tipo;
	}
	public void setTipo(TIPO tipo) {
		this.tipo = tipo;
	}

	public int getIndicetavolo() {
		return indicetavolo;
	}

	public void setIndicetavolo(int indicetavolo) {
		this.indicetavolo = indicetavolo;
	}

	@Override
	public int compareTo(Evento ev) {
		return this.time.compareTo(ev.getTime());
	}
	
}
