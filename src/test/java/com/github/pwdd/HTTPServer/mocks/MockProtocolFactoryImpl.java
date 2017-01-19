package com.github.pwdd.HTTPServer.mocks;

import com.github.pwdd.HTTPServer.protocols.AProtocol;
import com.github.pwdd.HTTPServer.protocols.AProtocolFactory;
import com.github.pwdd.HTTPServer.responders.IResponder;

import java.util.HashMap;

public class MockProtocolFactoryImpl extends AProtocolFactory {

  @Override
  public AProtocol createProtocol(String rootDirectory, HashMap<String, String> request) {
    return new MockProtocolImpl(rootDirectory, request, new IResponder[]{ new MockResponder() });
  }
}
