package com.exasol.adapter.document.files.stringfilter.wildcardexpression.parser;

import java.util.Queue;

import com.exasol.adapter.document.files.stringfilter.wildcardexpression.WildcardExpression;
import com.exasol.adapter.document.files.stringfilter.wildcardexpression.WildcardExpressionFragment;
import com.exasol.adapter.document.files.stringfilter.wildcardexpression.fragments.*;

/**
 * This class builds {@link WildcardExpression}s form GLOB expressions.
 */
public class GlobParser extends AbstractParser {

    /**
     * Create a new instance of {@link GlobParser}.
     */
    public GlobParser() {
        super(GlobConstants.ESCAPE_CHAR, GlobConstants.MULTI_CHAR_WILDCARD, GlobConstants.SINGLE_CHAR_WILDCARD);
    }

    @Override
    protected WildcardExpressionFragment getSingleCharWildcard(final Queue<Character> characterQueue) {
        return new DirectoryLimitedSingleCharWildcard();
    }

    @Override
    protected WildcardExpressionFragment getMultiCharWildcard(final Queue<Character> characterQueue) {
        final Character nextChar = characterQueue.peek();
        if (nextChar != null && nextChar.equals(GlobConstants.MULTI_CHAR_WILDCARD)) {
            characterQueue.remove();
            return new CrossDirectoryMultiCharWildcard();
        } else {
            return new DirectoryLimitedMultiCharWildcard();
        }
    }

    /**
     * Constants for GLOB expressions.
     */
    public static class GlobConstants {
        /** Glob escape character */
        public static final char ESCAPE_CHAR = '\\';
        /** Glob wildcard that matches multiple characters */
        public static final char MULTI_CHAR_WILDCARD = '*';
        /** Glob wildcard that matches one character */
        public static final char SINGLE_CHAR_WILDCARD = '?';

        private GlobConstants() {
            // empty on purpose
        }
    }
}
