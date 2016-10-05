package com.ejdoab.tcms.admin.remote;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.ejdoab.tcms.services.ConferenceServicesHome;
import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;
import com.ejdoab.tcms.services.dto.NewsItemDTO;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;
import com.ejdoab.tcms.services.dto.page.Page;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

/**
 * A local proxy to a remote service
 * @author Brian Sam-Bodden
 */
public class ConferenceServicesProxy {

	private ServiceLocator _locator;
	private ConferenceServicesHome _home;
	private com.ejdoab.tcms.services.ConferenceServices _service = null;

	/**
	 * 
	 */
	public ConferenceServicesProxy(ServiceLocator locator)
		throws RemoteServerException {
		_locator = locator;
		getService();
	}

	private void getService() throws RemoteServerException {
		try {
			_home =
				(ConferenceServicesHome) _locator.getRemoteHome(
					"ejb.ConferenceServicesHome",
					ConferenceServicesHome.class);
			_service = _home.create();
		} catch (ServiceLocatorException sle) {
			throw new RemoteServerException(sle);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		} catch (CreateException ce) {
			throw new RemoteServerException(ce);
		}
	}


	public Page getSessions(int start, int size) throws DTOCreateException, RemoteServerException {
		Page page = null;
		try {
			page = _service.getSessions(start, size);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		} 	
		return page;
	}


	public Page getSessionsByPresenter(String email, int start, int size)
		throws NoSuchUserException, DTOCreateException, RemoteServerException {
		Page page = null;
		try {
			page = _service.getSessionsByPresenter(email, start, size);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return page;
	}


	public boolean submitAbstract(ConferenceAbstractDTO dto)
		throws DTOUpdateException, RemoteServerException {
		boolean result = false;
		try {
			result = _service.submitAbstract(dto);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return result;
	}


	public ConferenceAbstractDTO getAbstract(int id)
		throws DTOCreateException, RemoteServerException {
		ConferenceAbstractDTO dto = null;
		try {
			dto = _service.getAbstract(id);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return dto;
	}


	public int getAbstractsCount() throws RemoteServerException {
		int result = -1;
		try {
			result = _service.getAbstractsCount();
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return result;
	}


	public Page getAbstracts(int start, int size) throws DTOCreateException, RemoteServerException {
		Page page = null;
		try {
			page = _service.getAbstracts(start, size);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return page;
	}


	public Page getAbstractsByPresenter(String email, int start, int size)
		throws NoSuchUserException, DTOCreateException, RemoteServerException {
		Page page = null;
		try {
			page = _service.getAbstractsByPresenter(email, start, size);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return page;
	}

	/**
	 * @return
	 * @throws DTOCreateException
	 * @throws RemoteServerException
	 */
	public ConferenceAbstractDTO[] getAbstractAsArray() throws DTOCreateException, RemoteServerException {
		ConferenceAbstractDTO[] abstracts = new ConferenceAbstractDTO[0];
		try {
			int count = _service.getAbstractsCount();
			System.out.println("count =" + count);

			abstracts =
				new ConferenceAbstractDTO[count];

			Page page = _service.getAbstracts(0, count);

			int i = 0;
			while (page.hasNext()) {
				abstracts[i] = (ConferenceAbstractDTO) page.next();
				System.out.println(abstracts[i]);
				i = i + 1;
			}		 
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}

		return abstracts;
	}	
	
	public java.lang.String[] getValidAbstractStatus() throws RemoteServerException {
		String[] result = null;
		try {
             result = _service.getValidAbstractStatus();
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}	
		return result;	
	}

	public java.lang.String[] getValidLevels() throws RemoteServerException {
		String[] result = null;
		try {
			 result = _service.getValidLevels();
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}	
		return result;
	}

	public java.lang.String[] getValidTopics() throws RemoteServerException {
		String[] result = null;
		try {
			 result = _service.getValidTopics();
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}	
		return result;		
	}

	public java.lang.String[] getValidTypes() throws RemoteServerException {
		String[] result = null;
		try {
			 result = _service.getValidTypes();
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}	
		return result;			
	}	
	
	/**
	 * @return
	 * @throws DTOCreateException
	 * @throws RemoteServerException
	 */
	public NewsItemDTO[] getNewsAsArray() throws DTOCreateException, RemoteServerException {
		NewsItemDTO[] news = new NewsItemDTO[0];
		try {
			int count = _service.getNewsCount();
			System.out.println("count =" + count);

			news =
				new NewsItemDTO[count];

			Page page = _service.getNews(0, count);

			int i = 0;
			while (page.hasNext()) {
				news[i] = (NewsItemDTO) page.next();
				System.out.println(news[i]);
				i = i + 1;
			}		 
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}

		return news;
	}	
	
	public boolean submitNewsItem(NewsItemDTO dto)
		throws DTOUpdateException, RemoteServerException {
		boolean result = false;
		try {
			result = _service.submitNewsItem(dto);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return result;
	}	
	
	public boolean removeNewsItem(NewsItemDTO dto)
		throws DTOUpdateException, RemoteServerException {
		boolean result = false;
		try {
			result = _service.removeNewsItem(dto);
		} catch (RemoteException re) {
			throw new RemoteServerException(re);
		}
		return result;
	}	
	
	

}
