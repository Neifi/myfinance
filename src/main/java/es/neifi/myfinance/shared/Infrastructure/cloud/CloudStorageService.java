package es.neifi.myfinance.shared.Infrastructure.cloud;

import es.neifi.myfinance.shared.domain.UploadContent;

public interface CloudStorageService {

    void store(UploadContent uploadContent);
    Object retrieve(String id);
}
