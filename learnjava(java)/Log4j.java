package door;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4j {
	Logger logger = null;
	ClassLoader loader = null;
	URL url = null;
	public Log4j(String method){
		String key = method + ".class";
		logger = Logger.getLogger (key);
		loader = Thread.currentThread().getContextClassLoader();
		url = loader.getResource("log4j.properties");
		PropertyConfigurator.configure(url);
	}

	public void write(String msg , int type){
		switch(type){
		case 1:
			logger.fatal(msg);
			break;
		case 2:
			logger.error(msg);
			break;
		case 3:
			logger.warn(msg);
			break;
		case 4:
			logger.info(msg);
			break;
		case 5:
			logger.debug(msg);
			break;
		case 6:
			logger.trace(msg);
			break;
		}
	}

	public void write(Exception msg , int type){
		switch(type){
		case 1:
			logger.fatal(msg);
			break;
		case 2:
			logger.error(msg);
			break;
		case 3:
			logger.warn(msg);
			break;
		case 4:
			logger.info(msg);
			break;
		case 5:
			logger.debug(msg);
			break;
		case 6:
			logger.trace(msg);
			break;
		}
	}

}
