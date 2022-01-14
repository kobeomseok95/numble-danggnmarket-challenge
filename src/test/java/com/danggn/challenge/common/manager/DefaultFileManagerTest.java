package com.danggn.challenge.common.manager;

import com.danggn.challenge.common.client.StorageClient;
import com.danggn.challenge.common.manager.file.DefaultFileManager;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultFileManagerTest {

    @Mock
    StorageClient storageClient;

    @InjectMocks
    DefaultFileManager defaultFileManager;

    @Test
    @DisplayName("업로드 / 성공")
    void upload_success() throws Exception {

        // given
        List<MultipartFile> files = createMockMultipartFiles();

        // when
        List<String> uploadedFileNames = defaultFileManager.upload(files);

        // then
        assertAll(
                () -> verify(storageClient, times(3)).upload(any(), any(), any()),
                () -> assertNotEquals(files.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.toList()), uploadedFileNames)
        );
    }

    private List<MultipartFile> createMockMultipartFiles() {
        return List.of(
                new MockMultipartFile("test1.jpg", "test1.jpg", ContentType.IMAGE_JPEG.getMimeType(), "test1.jpg".getBytes()),
                new MockMultipartFile("test2.jpg", "test2.jpg", ContentType.IMAGE_JPEG.getMimeType(), "test2.jpg".getBytes()),
                new MockMultipartFile("test3.jpg", "test3.jpg", ContentType.IMAGE_JPEG.getMimeType(), "test3.jpg".getBytes())
        );
    }
}