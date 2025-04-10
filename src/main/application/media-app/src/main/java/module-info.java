/**
 * Module for the business logic
 * The application (Hexagon)
 */
module media.app {
	/**
	 * Modules used by this module.
	 * "static": Module used at compile time, not need ed at runtime.
	 */
	requires media.lib;
	requires static lombok;
	requires jdk.jconsole;
    requires reactor.core;

    /**
	 * Packages published to other modules
	*/
	// driving ports
	exports com.newsnow.media.app.domain;
	exports com.newsnow.media.app.ports.driving;
	exports com.newsnow.media.app.ports.driving.formanagetask;
	// driven ports

}
