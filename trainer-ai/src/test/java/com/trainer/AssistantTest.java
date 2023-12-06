package com.trainer;


import com.trainer.models.OpenAiResponse;
import com.trainer.models.api.assistants.*;
import com.trainer.models.api.file.File;
import com.trainer.models.common.DeleteResult;
import com.trainer.models.common.ListSearchParameters;
import com.trainer.service.OpenAiService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OpenAiService.class)
public class AssistantTest {
    public static final String MATH_TUTOR = "Math Tutor";
    public static final String ASSISTANT_INSTRUCTION = "You are a personal Math Tutor.";

    static String token = System.getenv("OPENAI_TOKEN");;
    @Autowired
    private OpenAiService service;

    @Test
    void retrieveAssistant() {
        Assistant retrieveAssistantResponse = service.retrieveAssistant("asst_PzeRxIxcPcJ2oxRuTZK6VB99");
        System.out.println(retrieveAssistantResponse.getName());
    }

    @Test
    void modifyAssistant() {
        Assistant createAssistantResponse = createAndValidateAssistant();

        String modifiedName = MATH_TUTOR + "Modified";
        ModifyAssistantRequest modifyRequest = ModifyAssistantRequest.builder()
                .name(modifiedName)
                .build();

        Assistant modifiedAssistantResponse = service.modifyAssistant(createAssistantResponse.getId(), modifyRequest);
        assertNotNull(modifiedAssistantResponse);
        assertEquals(modifiedName, modifiedAssistantResponse.getName());
    }

    @Test
    void deleteAssistant() {
        Assistant createAssistantResponse = createAndValidateAssistant();

        DeleteResult deletedAssistant = service.deleteAssistant(createAssistantResponse.getId());

        assertNotNull(deletedAssistant);
        assertEquals(createAssistantResponse.getId(), deletedAssistant.getId());
        assertTrue(deletedAssistant.isDeleted());
    }

    @Test
    void listAssistants() {
        OpenAiResponse<Assistant> assistants = service.listAssistants(ListSearchParameters.builder().build());
        assistants.data.forEach(System.out::println);
    }

    @Test
    void createAssistantFile() {
        File uploadedFile = uploadAssistantFile();

        Assistant assistant = createAndValidateAssistant();

        AssistantFile assistantFile = service.createAssistantFile(assistant.getId(), new AssistantFileRequest(uploadedFile.getId()));

        assertNotNull(assistantFile);
        assertEquals(uploadedFile.getId(), assistantFile.getId());
        assertEquals(assistant.getId(), assistantFile.getAssistantId());
    }

    @Test
    void retrieveAssistantFile() {
        //TODO
        //There is a bug with uploading assistant files https://community.openai.com/t/possible-bug-with-agent-creation-php-file-upload/484490/5
        //So this would have to be done later
    }

    @Test
    void deleteAssistantFile() {
        //TODO
        //There is a bug with uploading assistant files https://community.openai.com/t/possible-bug-with-agent-creation-php-file-upload/484490/5
        //So this would have to be done later
    }

    @Test
    void listAssistantFiles() {
        //TODO
        //There is a bug with uploading assistant files https://community.openai.com/t/possible-bug-with-agent-creation-php-file-upload/484490/5
        //So this would have to be done later
    }

    @AfterAll
    static void clean() {
        //Clean up all data created during this test
        ListSearchParameters queryFilter = ListSearchParameters.builder()
                .limit(100)
                .build();

    }

    private  File uploadAssistantFile() {
        String filePath = "src/test/resources/Personal Trainer_3rd Edition_Text.pdf";
        return service.uploadFile("assistants", filePath);
    }

    private  Assistant createAndValidateAssistant() {
        AssistantRequest assistantRequest = assistantStub();
        Assistant createAssistantResponse = service.createAssistant(assistantRequest);
        validateAssistantResponse(createAssistantResponse);

        return createAssistantResponse;
    }

    private static AssistantRequest assistantStub() {
        return AssistantRequest.builder()
                .model("gpt-3.5-turbo")
                .name(MATH_TUTOR)
                .instructions(ASSISTANT_INSTRUCTION)
                .tools(Collections.singletonList(new Tool(AssistantToolsEnum.CODE_INTERPRETER, null)))
                .build();
    }

    private static void validateAssistantResponse(Assistant assistantResponse) {
        assertNotNull(assistantResponse);
        assertNotNull(assistantResponse.getId());
        assertNotNull(assistantResponse.getCreatedAt());
        assertNotNull(assistantResponse.getObject());
        assertEquals(assistantResponse.getTools().get(0).getType(),  AssistantToolsEnum.CODE_INTERPRETER);
        assertEquals(MATH_TUTOR, assistantResponse.getName());
    }
}
