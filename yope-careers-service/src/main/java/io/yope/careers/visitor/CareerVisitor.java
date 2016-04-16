package io.yope.careers.visitor;

import io.yope.ethereum.visitor.BlockchainVisitor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public abstract class CareerVisitor extends BlockchainVisitor {

    @Override
    public String getContractContent() {
        return removeLineBreaksFromFile(getContractFile());
    }

    @Override
    public Object[] getRunArgs() {
        return new Object[0];
    }

    private String removeLineBreaksFromFile(final String file) {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(file);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String collect = buffer.lines().collect(Collectors.joining("\n"));
        return collect.replace("\n", "").replace("\r", "");
    }
}
