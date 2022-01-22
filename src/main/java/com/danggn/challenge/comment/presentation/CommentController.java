package com.danggn.challenge.comment.presentation;

import com.danggn.challenge.comment.application.CommentUseCase;
import com.danggn.challenge.comment.presentation.request.CreateCommentRequest;
import com.danggn.challenge.comment.presentation.request.DeleteCommentRequest;
import com.danggn.challenge.comment.presentation.request.UpdateCommentRequest;
import com.danggn.challenge.common.security.AuthUser;
import com.danggn.challenge.common.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentUseCase commentUseCase;

    @GetMapping("")
    public String getComments(@RequestParam("productId") Long productId,
                              Model model) {
        model.addAttribute("comments", commentUseCase.getProductComments(productId));
        return "comment/comments";
    }

    @GetMapping("/add")
    public String addCommentView(@RequestParam("productId") Long productId,
                                 Model model) {
        model.addAttribute("createCommentRequest", new CreateCommentRequest(productId));
        return "comment/addForm";
    }

    @PostMapping("/add")
    public String addComment(@Valid @ModelAttribute CreateCommentRequest createCommentRequest,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @AuthUser LoginMember loginMember) {
        if (bindingResult.hasErrors()) {
            return "comment/addForm";
        }

        commentUseCase.save(
                CommentPresentationAssembler.toCreateCommentRequestVo(createCommentRequest),
                loginMember);
        return "redirect:/comments?productId=" + createCommentRequest.getProductId();
    }

    @GetMapping("/{commentId}/edit")
    public String editCommentView(@PathVariable("commentId") Long commentId,
                                  Model model) {
        UpdateCommentRequest updateCommentRequest = CommentPresentationAssembler.toUpdateCommentRequest(
                commentUseCase.getComment(commentId));
        model.addAttribute("updateCommentRequest", updateCommentRequest);
        return "comment/editForm";
    }

    @PostMapping("/edit")
    public String editComment(@Valid @ModelAttribute UpdateCommentRequest updateCommentRequest,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updateCommentRequest", updateCommentRequest);
            return "comment/editForm";
        }

        Long updatedCommentProductId = commentUseCase.update(
                CommentPresentationAssembler.toUpdateCommentRequestVo(updateCommentRequest));
        return "redirect:/comments?productId=" + updatedCommentProductId;
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId,
                                @ModelAttribute DeleteCommentRequest deleteCommentRequest) {
        commentUseCase.delete(commentId);
        return "redirect:/comments?productId=" + deleteCommentRequest.getProductId();
    }
}
