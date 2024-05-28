package me.ks.chan.pica.plus.util.kotlin

/**
 * Schrödinger's cat:
 * Observe if the cat is alive or dead.
 **/
inline fun <reified T, reified C: T, reified O> T.observe(
    mapping: C.() -> O
): O? {
    return (this as? C)?.let(mapping)
}