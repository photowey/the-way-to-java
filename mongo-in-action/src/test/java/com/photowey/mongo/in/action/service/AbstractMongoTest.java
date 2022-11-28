/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.mongo.in.action.service;

import com.photowey.mongo.in.action.domain.entity.Book;
import com.photowey.mongo.in.action.engine.IMongoEngine;
import com.photowey.mongo.in.action.service.impl.AwareBookService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@code AbstractMongoTest}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
public abstract class AbstractMongoTest {

    @Autowired
    protected IMongoEngine mongoEngine;

    @Autowired
    protected AwareBookService awareBookService;

    protected Book populateBook(String nextId, int price) {
        Book book = new Book();
        book.setId(nextId);
        book.setBusinessId(nextId);
        book.setPrice(price);
        book.setName("《双城记》");
        book.setInfo(INFO);
        book.setPublish("工业出版社");
        return book;
    }

    protected static final String INFO = "1757年 [2]  12月的一个月夜，寓居巴黎的年轻医生马内特散步时，" +
            "突然被厄弗里蒙地侯爵兄弟强迫出诊。在侯爵府第中，他目睹一个发狂的绝色农妇和一个身受剑伤的少年饮恨而死的惨状，" +
            "原来姐弟二人是侯爵家的佃户，侯爵兄弟为了片刻淫乐杀害了他们全家。他拒绝侯爵兄弟的重金贿赂，写信向朝廷告发。不料控告信落到被告人手中，医生被关进巴士底狱，从此与世隔绝，杳无音讯。\n" +
            "尽管人们费尽心机，寻找他的下落，都毫无结果。他妻子因失去丈夫而痛苦万分。她决定不让小女儿知道她父亲的厄运，并在此情况下把她抚养成人。两年后，她不幸去世，临终前她委托特尔森银行担任小露西的监护者，因多年来医生的财务都是托付该银行经管的。幼小的孤女露西被银行家洛里接到伦敦，在善良的女仆普洛斯抚养下长大。\n" +
            "18年后，马内特医生获释，但身心受到极度摧残的他对周围事物已完全失去记忆。这位精神失常的白发老人被巴黎圣安东尼区的一名酒贩、他旧日的仆人德发日收留。这时，女儿露西已经长大成人，专程接他去英国居住。旅途上，他们邂逅法国青年查理·达尔奈，受到他的细心照料。原来达尔奈就是侯爵的侄子。 [16]  他憎恨自己家族的罪恶，毅然放弃财产的继承权和贵族的姓氏，移居伦敦，当了一名法语教师。\n" +
            "到英国后不久，达尔奈被一场莫须有的官司缠身，他所聘请的律师帮他打赢了官司。律师的助手西德尼·卡顿与达尔奈相貌酷似。卡顿和达尔奈很快成为医生家的常客。经过露西的细心照料和无微不至的关怀，医生几乎已完全摆脱了他那长期的牢狱生活的阴影，只是在他非常激动的时刻，人们才会觉察到他过去精神错乱时的一些痕迹。露西可爱的容貌使这两位年轻人都为之倾倒，但露西爱慕的只是达尔奈。马内特为了女儿的幸福，决定埋葬过去，欣然同意他们的婚事。\n" +
            "在法国，达尔奈父母相继去世，叔父厄弗里蒙地侯爵继续为所欲为。当他驾驶狂奔的马车若无其事地轧死一个农民的孩子后，终于被孩子父亲用刀杀死。一场革命的风暴正在酝酿之中，德发日的酒店就是革命活动的联络点，他的妻子不停地把贵族的暴行编织成不同的花纹，记录在围巾上，渴望复仇。\n" +
            "1789年 [3]  法国大革命的风暴终于袭来了。巴黎人民攻占了巴士底狱，把贵族一个个送上断头台。远在伦敦的达尔奈为了营救管家盖白勒，冒险回国，一到巴黎就被捕入狱。这些消息很快传到了伦敦。医生带着女儿露西赶往巴黎，因为他相信他在巴士底狱长期被监禁的经历会博得法国人民的同情，从而能有助于搭救他的女婿。随着时间一天天、一月月的流逝，医生终于获得了不处死达尔奈的诺言。\n" +
            "情况在最后发生了逆转，在巴士底狱遗址中发现了一个文件，其中包括马内特医生关于自己如何被劫持和监禁的叙述以及对侯爵家族及其后代的严肃的诅咒，“向苍天和大地控告厄弗里蒙地家族的最后一个人。”法庭判处达尔奈在二十四小时内将被执行死刑。\n" +
            "此时，卡顿随后来到了巴黎。他曾经答应过露西为了拯救她所爱的人不惜牺牲自己。通过贿赂，他获准进入了监狱。利用自己的相貌与达尔奈十分相像这一点，他把被麻醉的达尔奈挪出牢房，自己代替达尔奈在牢房里，等待着他的末日来临。马内特父女早已准备就绪，达尔奈一到，马上出发。一行人顺利地离开法国。\n" +
            "沿着巴黎的街道，六辆押送死囚的囚车装载着当天去祭断头台的祭品。卡顿坐在第三辆囚车里，他的手被捆绑着。当他听到街上有反对他的呼喊声时，只是平静地微微一笑，同时把头发摆动得更松散些以遮盖他的脸部。在断头台上，卡顿为了爱情，从容献身。 [4] ";
}
