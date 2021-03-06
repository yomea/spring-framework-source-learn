/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.portlet.handler;

import javax.portlet.PortletMode;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.portlet.MockPortletContext;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.web.portlet.context.ConfigurablePortletApplicationContext;
import org.springframework.web.portlet.context.XmlPortletApplicationContext;

import static org.junit.Assert.*;

/**
 * @author Mark Fisher
 * @author Sam Brannen
 */
public class PortletModeHandlerMappingTests {

	public static final String CONF = "/org/springframework/web/portlet/handler/portletModeMapping.xml";

	private final ConfigurablePortletApplicationContext pac = new XmlPortletApplicationContext();

	private final MockPortletContext portletContext = new MockPortletContext();

	private final MockPortletRequest request = new MockPortletRequest();

	private PortletModeHandlerMapping hm;


	@Before
	public void setUp() throws Exception {
		pac.setPortletContext(portletContext);
		pac.setConfigLocations(new String[] {CONF});
		pac.refresh();

		hm = pac.getBean(PortletModeHandlerMapping.class);
	}

	@Test
	public void portletModeView() throws Exception {
		request.setPortletMode(PortletMode.VIEW);

		Object handler = hm.getHandler(request).getHandler();
		assertEquals(pac.getBean("viewHandler"), handler);
	}

	@Test
	public void portletModeEdit() throws Exception {
		request.setPortletMode(PortletMode.EDIT);

		Object handler = hm.getHandler(request).getHandler();
		assertEquals(pac.getBean("editHandler"), handler);
	}

	@Test
	public void portletModeHelp() throws Exception {
		request.setPortletMode(PortletMode.HELP);

		Object handler = hm.getHandler(request).getHandler();
		assertEquals(pac.getBean("helpHandler"), handler);
	}

	@Test(expected = IllegalStateException.class)
	public void duplicateMappingAttempt() {
		hm.registerHandler(PortletMode.VIEW, new Object());
	}

}
