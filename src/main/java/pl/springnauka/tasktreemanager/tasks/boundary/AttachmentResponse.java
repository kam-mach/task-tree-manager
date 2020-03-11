package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.springnauka.tasktreemanager.tasks.entity.Attachment;

@Data
@AllArgsConstructor
class AttachmentResponse {
    String filename;
    String comment;

    static AttachmentResponse from(Attachment attachment) {
        return new AttachmentResponse(attachment.getFilename(), attachment.getComment());
    }
}
