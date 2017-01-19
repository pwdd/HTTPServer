package com.github.pwdd.HTTPServer.protocols;

import com.github.pwdd.HTTPServer.responders.IResponder;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public abstract class AProtocol {
  String rootDirectory;
  HashMap<String, String> request;
  IResponder[] responders;

  public AProtocol(String _rootDirectory, HashMap<String, String> _request, IResponder[] _responders) {
    this.rootDirectory = _rootDirectory;
    this.request = _request;
    this.responders = _responders;
  }

  public abstract InputStream errorMessage();

  public InputStream processResponse() throws IOException {
    String uri = request.get("URI");
    String fullPath = getFullPath(uri);
    for (IResponder responder : responders) {
      if (responder.canRespond(fullPath)) {
        return buildFrom(responder.header(fullPath, dateInUTC0()), responder.body(fullPath));
      }
    }
    return errorMessage();
  }

  private InputStream buildFrom(InputStream header, InputStream body) {
    return new SequenceInputStream(header, body);
  }

  private String dateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }

  private String getFullPath(String uri) {
    Path path = Paths.get(System.getProperty("user.dir"), rootDirectory + uri);
    return Files.exists(path) ? path.toString() : uri;
  }
}
