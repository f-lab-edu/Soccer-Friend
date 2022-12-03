package soccerfriend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soccerfriend.dto.PostImage;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.PostImageMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static soccerfriend.exception.ExceptionInfo.IMAGE_NOT_EXIST;

@Service
public class PostImageService {

    private final PostImageMapper mapper;
    private final FileManageService fileManageService;
    private final String path;

    public PostImageService(PostImageMapper mapper, FileManageService fileManageService,
                            @Value("${ncp.storage.postImage.folder}") String path) {
        this.mapper = mapper;
        this.fileManageService = fileManageService;
        this.path = path;
    }

    /**
     * 게시물과 함께 첨부된 이미지들을 업로드합니다.
     *
     * @param postId 게시물의 id
     * @param images 이미지들의 정보
     */
    public void uploadImage(int postId, List<MultipartFile> images) {
        if (images.isEmpty()) {
            throw new BadRequestException(IMAGE_NOT_EXIST);
        }

        for (MultipartFile image : images) {
            File file = new File("POST_" + postId + "_" + image.getName());

            try {
                image.transferTo(file);
                fileManageService.upload(file);
            } catch (IOException e) {
                throw new BadRequestException(IMAGE_NOT_EXIST);
            }

            PostImage postImage = PostImage.builder()
                                           .postId(postId)
                                           .fileName(file.getName())
                                           .path(path)
                                           .build();

            mapper.insert(postImage);
        }
    }
}
