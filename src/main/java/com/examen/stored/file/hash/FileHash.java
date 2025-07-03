package com.examen.stored.file.hash;

import com.examen.stored.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
