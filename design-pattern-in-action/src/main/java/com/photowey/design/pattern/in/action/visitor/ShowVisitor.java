/*
 * Copyright © 2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.design.pattern.in.action.visitor;

/**
 * {@code ShowVisitor}
 *
 * @author photowey
 * @date 2022/03/04
 * @since 1.0.0
 */
public class ShowVisitor implements Visitor {

    @Override
    public void visit(Bachelor bachelor) {
        System.out.println("A bachelor\n");
        this.printMessage(bachelor);
    }

    @Override
    public void visit(College college) {
        System.out.println(" a college student!\n");
        this.printMessage(college);
    }

    public void printMessage(Student student) {
        System.out.println("Name : " + student.getName() + "\n"
                + "University : " + student.getUniversity() + "\n"
                + "Rating : " + student.getRating() + "\n"
        );
    }
}
