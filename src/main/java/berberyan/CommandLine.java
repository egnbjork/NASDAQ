package berberyan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandLine {
	private static Logger logger = LogManager.getLogger(Nasdaq.class);
	
	public static void main(String[] args) {
		new Cli(args).parse();
	}

}
