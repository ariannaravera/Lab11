package it.polito.tdp.bar.model;

public class Model {

	public String simula() {
		Simulatore sim=new Simulatore();
		sim.init();
		return sim.run();
	}

}
