package it.prova.manytomanybranogeneremaven.service;

import it.prova.manytomanybranogeneremaven.dao.MyDaoFactory;

public class MyServiceFactory {

	private static BranoService branoServiceInstance = null;
	private static GenereService genereServiceInstance = null;

	public static BranoService getBranoServiceInstance() {
		if (branoServiceInstance == null)
			branoServiceInstance = new BranoServiceImpl();

		branoServiceInstance.setBranoDAO(MyDaoFactory.getBranoDAOInstance());

		return branoServiceInstance;
	}

	public static GenereService getGenereServiceInstance() {
		if (genereServiceInstance == null)
			genereServiceInstance = new GenereServiceImpl();

		genereServiceInstance.setGenereDAO(MyDaoFactory.getGenereDAOInstance());

		return genereServiceInstance;
	}

}
