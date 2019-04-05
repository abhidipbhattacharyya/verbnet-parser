package io.github.clearwsd.semlink.aligner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.mit.jverbnet.data.syntax.ISyntaxArgDesc;
import io.github.clearwsd.parser.Proposition;
import io.github.clearwsd.propbank.type.ArgNumber;
import io.github.clearwsd.propbank.type.PropBankArg;
import io.github.clearwsd.semlink.Alignment;
import io.github.clearwsd.semlink.PropBankPhrase;
import io.github.clearwsd.verbnet.VerbNetClass;
import io.github.clearwsd.verbnet.type.FramePhrase;
import io.github.clearwsd.verbnet.type.SyntacticFrame;
import io.github.clearwsd.verbnet.type.ThematicRoleType;
import io.github.clearwsd.verbnet.type.VerbNetSyntaxType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import static io.github.clearwsd.semlink.PbVnMappings.Roleset;

/**
 * PropBank Verbnet candidate alignment context.
 *
 * @author jgung
 */
@Getter
@Setter
@Accessors(fluent = true)
public class PbVnAlignment {

    @Delegate
    private Alignment<PropBankPhrase, FramePhrase> alignment;
    private List<PropBankPhrase> propbankPhrases;
    private SyntacticFrame frame;
    private List<Roleset> rolesets;
    private ISyntaxArgDesc syntaxArgDesc;
    private Proposition<VerbNetClass, PropBankArg> proposition;

    public List<PropBankPhrase> byNumber(@NonNull ArgNumber number) {
        return propbankPhrases.stream()
                .filter(s -> s.getNumber() == number)
                .collect(Collectors.toList());
    }

    public <T extends FramePhrase> List<T> bySyntacticType(@NonNull VerbNetSyntaxType type) {
        //noinspection unchecked
        return (List<T>) frame.phrases(type);
    }

    public Optional<FramePhrase> byRole(@NonNull ThematicRoleType role) {
        return frame.role(role);
    }

    @Override
    public String toString() {
        return alignment.toString();
    }

}
