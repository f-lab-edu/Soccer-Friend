package soccerfriend.service;

import com.amazonaws.SdkClientException;
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
                            @Value("${s3.storage.postImage.folder}") String path) {
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
                deleteImageByException(postId, images);
            }

            PostImage postImage = PostImage.builder()
                                           .postId(postId)
                                           .fileName(file.getName())
                                           .path(path)
                                           .build();

            mapper.insert(postImage);
        }
    }

    /**
     * uploadImage 과정에서 IOException이 발생했을 때 이전에 업로드된 이미지들을 삭제한다.
     *
     * @param postId 게시물의 id
     * @param images 이미지들의 정보
     */
    public void deleteImageByException(int postId, List<MultipartFile> images) {
        if (images.isEmpty()) {
            throw new BadRequestException(IMAGE_NOT_EXIST);
        }

        for (MultipartFile image : images) {
            String fileName = "POST_" + postId + "_" + image.getName();
            try {
                fileManageService.delete(fileName);
            } catch (SdkClientException e) {
                throw new BadRequestException(IMAGE_NOT_EXIST);
            }
        }
    }

    /**
     * 특정 id의 게시물에 업로드된 사진을 삭제합니다. 이 때 디스크에 저장된 파일은 삭제되지 않습니다.
     *
     * @param id 게시물에 업로드된 사진의 id
     */
    public void delete(int id) {
        PostImage postImage = getPostImageById(id);
        if (postImage == null) {
            throw new BadRequestException(IMAGE_NOT_EXIST);
        }

        mapper.delete(id);
    }

    /**
     * 특정 id의 게시물에 업로드된 사진을 반환합니다.
     *
     * @param id 게시물에 업로드된 사진의 id
     * @return 특정 id의 게시물에 업로드된 사진
     */
    public PostImage getPostImageById(int id) {
        return mapper.getPostImageById(id);
    }
}
