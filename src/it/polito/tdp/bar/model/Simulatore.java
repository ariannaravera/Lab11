package it.polito.tdp.bar.model;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;



public class Simulatore {
	
	//MODELLO/LO STATO DEL SISTEMA
	private Map<Integer, Tavolo> tavoli;
	private Map<Integer, Tavolo> tavoliLiberi;
	
	
		
	//VALORI IN OUTPUT
	private int nclienti;
	private int soddisfatti;
	private int insoddisfatti;
		
	//CODA
	private PriorityQueue<Evento> queue;

	public void init() {
		
		tavoli=new HashMap<>();
		tavoli.put(1, new Tavolo(1, 10, false));
		tavoli.put(2, new Tavolo(2, 10, false));
		tavoli.put(3,  new Tavolo(3, 8, false));
		tavoli.put(4, new Tavolo(4, 8, false));
		tavoli.put(5, new Tavolo(5, 8, false));
		tavoli.put(6, new Tavolo(6, 8, false));
		tavoli.put(7, new Tavolo(7, 6, false));
		tavoli.put(8, new Tavolo(8, 6, false));
		tavoli.put(9, new Tavolo(9, 6, false));
		tavoli.put(10, new Tavolo(10, 6, false));
		tavoli.put(11, new Tavolo(11, 4, false));
		tavoli.put(12, new Tavolo(12, 4, false));
		tavoli.put(13, new Tavolo(13, 4, false));
		tavoli.put(14, new Tavolo(14, 4, false));
		tavoli.put(15, new Tavolo(15, 4, false));
		
		this.queue = new PriorityQueue<Evento>();
		LocalTime inizio=LocalTime.of(8, 0, 0);
		tavoliLiberi=new HashMap<>(tavoli);
		
		//inserisco gli eventi iniziali
		for(int i=0; i<2000; i++) {
			Evento e = new Evento(inizio, (int)Math.random()*10, Math.random()*60+60, (float)Math.random()*9/10, Evento.TIPO.ARRIVO_GRUPPO_CLIENTI, -1);
			inizio=inizio.plusMinutes((long) (Math.random()*10));
			queue.add(e);
		}
	}

	public String run() {
		while (!queue.isEmpty()) {
			Evento e = queue.poll();
			nclienti+=e.getNum_persone();
			
			switch (e.getTipo()) {

			case ARRIVO_GRUPPO_CLIENTI:
				
				// guardo se ho tavoli liberi
				if(tavoliLiberi.size()>=0) {
					int indice=-1;
					
					// se ci sono guardo se almeno uno va bene per il gruppo
					for(Tavolo t:tavoliLiberi.values()) {
						if(t.getPosti()>=e.getNum_persone()&&t.getPosti()<=e.getNum_persone()*2) {
							indice=t.getId();
						}
					}
					
					//se ok, tolgo il tavolo da quelli liberi
					if(indice!=-1) {
						queue.add(new Evento(e.getTime().plusMinutes((long) e.getDurata()), e.getNum_persone(), 0.0, 0, Evento.TIPO.USCITA_GRUPPO_CLIENTI,indice));
						tavoliLiberi.remove(indice);
						soddisfatti+=e.getNum_persone();
					}
					
					//se no valuto la tolleranza per il bancone
					else if(e.getTolleranza()>0.0) {
						double prob=Math.random();
						if(prob<e.getTolleranza())
							soddisfatti+=e.getNum_persone();
						else
							insoddisfatti+=e.getNum_persone();
					}
					else {
						insoddisfatti+=e.getNum_persone();
					}
				}
				
				//se tolleranti vanno al bancone
				else if(e.getTolleranza()>0.0) {
					double prob=Math.random();
					if(prob<e.getTolleranza())
						soddisfatti+=e.getNum_persone();
					else
						insoddisfatti+=e.getNum_persone();
				}
				
				else {
					insoddisfatti+=e.getNum_persone();
				}
				
				break;
				
				
			case USCITA_GRUPPO_CLIENTI:
				tavoliLiberi.put(e.getIndicetavolo(), tavoli.get(e.getIndicetavolo()));
				break;
			}
		}
		return "Numero totale di clienti della gionata: "+nclienti+"\nClienti soddisfatti "+soddisfatti+"\nClienti insoddisfatti "+insoddisfatti;
	}

}
