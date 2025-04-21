package com.projects.modular.healthPlatform.model.result;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class CommentResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long commentId;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论人
     */
    private Long userId;

    /**
     * 活动
     */
    private Long forumId;

    /**
     * 评论时间
     */
    private Date createTime;
    private String userName;
    private String nickName;
    private String  head;
}
