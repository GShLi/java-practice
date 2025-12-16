package cn.doublefloat;

import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Double
 * @since 2025/6/18 23:57
 * @version 1.0
 */
public class GetRestTemplate {

  public static void main(String[] args) {
    RestTemplate restTemplate = new RestTemplate();
    get(restTemplate);
  }

  public static void get(RestTemplate restTemplate) {

    String url = "http://localhost:8080/demo/path/123";
    String resp = restTemplate.getForObject(url, String.class);
    System.out.println(resp);
  }

  public static void uploadWithParamsAndFile(
      RestTemplate restTemplate, String url, Map<String, String> params, MultipartFile file)
      throws IOException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    params.forEach(body::add);
    body.add(
        "file",
        new ByteArrayResource(file.getBytes()) {
          @Override
          public String getFilename() {
            return file.getOriginalFilename();
          }
        });

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
    System.out.println(response.getBody());
  }
}
