package io.github.clearwsd.semlink.aligner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.github.clearwsd.propbank.type.ArgNumber;
import io.github.clearwsd.semlink.PropBankPhrase;
import io.github.clearwsd.verbnet.type.FramePhrase;
import io.github.clearwsd.verbnet.type.ThematicRoleType;
import lombok.NonNull;

import static io.github.clearwsd.semlink.PbVnMappings.Roleset;

/**
 * Applies mappings use PB-VN mappings.
 *
 * @author jgung
 */
public class RoleMappingAligner implements PbVnAligner {

    @Override
    public void align(@NonNull PbVnAlignment alignment) {

        Map<PropBankPhrase, FramePhrase> best = new HashMap<>();

        for (Roleset roleset : alignment.rolesets()) {

            Map<PropBankPhrase, FramePhrase> current = new HashMap<>();
            for (PropBankPhrase source : alignment.sourcePhrases()) {

                ArgNumber number = source.getNumber();
                if (!roleset.roleMappings().containsKey(number)) {
                    continue;
                }
                for (String mapping : roleset.roleMappings().get(number)) {
                    Optional<ThematicRoleType> roleType = ThematicRoleType.fromString(mapping);
                    if (!roleType.isPresent()) {
                        continue;
                    }
                    Optional<FramePhrase> framePhrase = alignment.byRole(roleType.get());
                    if (framePhrase.isPresent()) {
                        current.put(source, framePhrase.get());
                        break;
                    }
                }
            }
            if (current.size() > best.size()) {
                best = current;
            }
        }

        for (Map.Entry<PropBankPhrase, FramePhrase> aligned : best.entrySet()) {
            alignment.add(aligned.getKey(), aligned.getValue());
        }

    }

}
