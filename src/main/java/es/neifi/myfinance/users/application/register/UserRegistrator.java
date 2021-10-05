package es.neifi.myfinance.users.application.register;

import es.neifi.myfinance.shared.Infrastructure.cloud.CloudStorageService;
import es.neifi.myfinance.shared.domain.UploadContent;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.domain.Avatar;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class UserRegistrator {

    private final UserRepository userRepository;
    private final EventBus eventBus;
    private final CloudStorageService cloudStorageService;

    public UserRegistrator(UserRepository userRepository, EventBus eventBus, CloudStorageService cloudStorageService) {
        this.userRepository = userRepository;
        this.eventBus = eventBus;
        this.cloudStorageService = cloudStorageService;
    }

    public void register(RegisterUserCommand request) {
        try {
            String avatarId = UUID.randomUUID().toString();

            uploadAvatar(request, avatarId);

            String avatarUrl = (String) cloudStorageService.retrieve(avatarId);

            User newUser = User.createUser(
                    new UserID(request.id()),
                    new UserName(request.name()),
                    new Email(request.email()),
                    new Avatar(avatarUrl)
            );

            this.userRepository.save(newUser);
            this.eventBus.publish(newUser.pullEvents());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadAvatar(RegisterUserCommand request, String avatarId) throws IOException {
        File dest = new File("temp/avatar/" + request.id()
                .concat(Objects.requireNonNull(request.avatar().getOriginalFilename())));

        request.avatar().getInputStream().transferTo(new FileOutputStream(dest));

        UploadContent avatarUploadContent = new UploadContent(dest.getAbsolutePath())
                .withUploadedOn(Timestamp.from(Instant.now()).toString())
                .withName(request.name().concat(" - avatar"))
                .withId(avatarId);
        avatarUploadContent.putMetadata("contentyType","image/png");
        cloudStorageService.store(avatarUploadContent);

        dest.delete();
    }
}
