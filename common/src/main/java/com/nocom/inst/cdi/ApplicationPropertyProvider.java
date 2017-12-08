package com.nocom.inst.cdi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class ApplicationPropertyProvider {

	private static Properties properties;

	@Produces
	@ApplicationProperty("")
	String findProperty(InjectionPoint ip) {
		ApplicationProperty annotation = ip.getAnnotated().getAnnotation(ApplicationProperty.class);
		String name = annotation.value();

		if (properties == null) {

			properties = new Properties();

			try {

				Files.walk(Paths.get(System.getProperty("jboss.server.config.dir") + File.separator + "backend-web")).filter(Files::isRegularFile).sorted(Comparator.comparing(Path::getFileName)).forEach((path) -> {
					try {
						properties.load(Files.newInputStream(path));

					} catch (IOException e) {
						throw new ContextedRuntimeException("Application property not found", e).addContextValue("property", name);
					}
				});

				// sorted(Comparator.comparing(Path::))

			} catch (Exception e) {
				throw new ContextedRuntimeException("Application property not found", e).addContextValue("property", name);
			}
		}

		String found = properties.getProperty(name);
		if (found == null) {
			throw new ContextedRuntimeException("Application property not found").addContextValue("property", name);
		}
		return found;

	}

}