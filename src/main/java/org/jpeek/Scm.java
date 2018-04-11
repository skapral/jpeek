package org.jpeek;

import com.jcabi.xml.XMLDocument;
import org.cactoos.iterable.ItemAt;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

import java.nio.file.Path;

/**
 * Source control management system.
 *
 * @author Sergey Kapralov (skapralov@mail.ru)
 * @version $Id$
 * @since 0.26.2
 */
public interface Scm {
    /**
     * Obtains SCM URI
     */
    String uri();

    final class FromPom {
        private final Path pom;

        /**
         * Ctor.
         *
         * @param pom path to POM file.
         */
        public FromPom(Path pom) {
            this.pom = pom;
        }

        /**
         * Obtains SCM URI from POM file.
         *
         * @return SCM URI address, if it is presented.
         */
        public String uri () {
            return new UncheckedScalar<>(
                new ItemAt<>(
                    0,
                    new XMLDocument(
                        new UncheckedText(
                            new TextOf(
                                pom
                            ),
                            e -> ""
                        ).asString()
                    )
                        .registerNs("m", "http://maven.apache.org/POM/4.0.0")
                        .xpath("//m:project/m:scm/m:url/text()")
                )
            ).value();
        }
    }
}
