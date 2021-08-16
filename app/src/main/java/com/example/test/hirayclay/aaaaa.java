//package com.example.test.hirayclay;
//
//import com.example.test.R;
//
//public class aaaaa {
//    //
////  TUViewController.m
////  CollectionView练习
////
////  Created by 流言 on 2021/8/12.
////  Copyright © 2021 菲比寻常. All rights reserved.
////
//
//#import "TUViewController.h"
//        #import "Masonry.h"
//
//        #define kDeviceWidth [UIScreen mainScreen].bounds.size.width
//#define kDeviceHeight [UIScreen mainScreen].bounds.size.height
//#define SET_COLOUR(R, G, B) [UIColor colorWithRed:(R)/255.0 green:(G)/255.0 blue:(B)/255.0 alpha:1]
//
//    /**
//     *  是否是刘海屏
//     */
//    static inline BOOL iPhoneX() {
//        CGFloat iPhoneNotchDirectionSafeAreaInsets = 0;
//        if (@available(iOS 11.0, *)) {
//            UIEdgeInsets safeAreaInsets = [UIApplication sharedApplication].windows[0].safeAreaInsets;
//            switch ([UIApplication sharedApplication].statusBarOrientation) {
//                case UIInterfaceOrientationPortrait:{
//                    iPhoneNotchDirectionSafeAreaInsets = safeAreaInsets.top;
//                } break;
//
//                case UIInterfaceOrientationLandscapeLeft:{
//                    iPhoneNotchDirectionSafeAreaInsets = safeAreaInsets.left;
//                } break;
//
//                case UIInterfaceOrientationLandscapeRight:{
//                    iPhoneNotchDirectionSafeAreaInsets = safeAreaInsets.right;
//                } break;
//
//                case UIInterfaceOrientationPortraitUpsideDown:{
//                    iPhoneNotchDirectionSafeAreaInsets = safeAreaInsets.bottom;
//                } break;
//
//                default:
//                    iPhoneNotchDirectionSafeAreaInsets = safeAreaInsets.top;
//                    break;
//            }
//        }
//        return iPhoneNotchDirectionSafeAreaInsets > 40;
//    }
//
//    @interface TUViewController () <UIScrollViewDelegate>  {
//        CGFloat overlapSet; // 重叠偏移量
//        CGFloat itemSpace;  // 间距
//        UIEdgeInsets contentEdge;   // 总内容上下左右间距
//    }
//
//    @property (nonatomic, strong) NSMutableArray *overlapViews;
//    @property (nonatomic, strong) UIView *redView;
//
//    @end
//
//    @implementation TUViewController
//
//- (void)viewDidLoad {
//    [super viewDidLoad];
//
//        contentEdge =UIEdgeInsetsMake(10, 15, 10, 10);
//        itemSpace =10;
//        overlapSet =15;
//
//        UIScrollView *bgScrollView =[[UIScrollView alloc] initWithFrame:CGRectMake(0, iPhoneX() ? 44:20, kDeviceWidth, 200)];
//        bgScrollView.contentSize =CGSizeMake(kDeviceWidth *1.75, bgScrollView.frame.size.height);
//        bgScrollView.showsHorizontalScrollIndicator =NO;
//        bgScrollView.delegate =self;
//        bgScrollView.decelerationRate =UIScrollViewDecelerationRateFast;
//    [self.view addSubview:bgScrollView];
//
//        UIView *bgView =[[UIView alloc] initWithFrame:CGRectMake(contentEdge.left, contentEdge.top, (kDeviceWidth-contentEdge.left-contentEdge.right-itemSpace)*0.7, CGRectGetHeight(bgScrollView.frame)-contentEdge.top-contentEdge.bottom)];
//        bgView.backgroundColor =[UIColor systemGrayColor];
//        bgView.layer.cornerRadius =8.0;
//        bgView.clipsToBounds =YES;
//    [bgScrollView addSubview:bgView];
//
//        UIView *redView =[[UIView alloc] initWithFrame:CGRectMake(CGRectGetMaxX(bgView.frame)+itemSpace, CGRectGetMinY(bgView.frame), (kDeviceWidth-contentEdge.left-contentEdge.right-itemSpace)*0.25, CGRectGetHeight(bgView.frame))];
//        redView.layer.cornerRadius =8.0;
//        redView.clipsToBounds =YES;
//        redView.backgroundColor =[UIColor redColor];
//
//
//        //* Masonry布局
//    [bgScrollView addSubview:redView];
//        self.redView =redView;
//
//        UIView *tempView =redView;
//        for (NSInteger i=0; i<3; i++) {
//            UIView *view =[[UIView alloc] initWithFrame:CGRectMake(CGRectGetMinX(redView.frame)+overlapSet*i, CGRectGetMinY(redView.frame), CGRectGetWidth(redView.frame), CGRectGetHeight(redView.frame))];
//            view.backgroundColor =SET_COLOUR(random()%205, random()%250, random()%245);
//            view.layer.cornerRadius =8.0;
//            view.clipsToBounds =YES;
//            view.alpha =1 -(i+1)*0.15;
//
//            CGFloat scale =(CGRectGetHeight(redView.frame)-overlapSet*(i+1))/CGRectGetHeight(redView.frame);
//            view.transform =CGAffineTransformMakeScale(scale, scale);
//        [bgScrollView insertSubview:view belowSubview:tempView];
//        [view mas_makeConstraints:^(MASConstraintMaker *make) {
//                make.centerY.mas_equalTo(redView.mas_centerY).offset(0);
//                make.left.mas_equalTo(tempView.mas_left).offset(overlapSet);
//                make.size.mas_equalTo(CGSizeMake(CGRectGetWidth(redView.frame), CGRectGetHeight(redView.frame)));
//            }];
//        [self.overlapViews addObject:view];
//            tempView =view;
//        }
//    }
//
//- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
//        CGFloat maxContentOffsetX =scrollView.contentSize.width-CGRectGetWidth(scrollView.frame);
//        // 每个item所占宽度
//        CGFloat itemWidth =CGRectGetWidth(self.redView.frame)+itemSpace;
//        if (scrollView.contentOffset.x>0 && scrollView.contentOffset.x < maxContentOffsetX) {
//            // 需要移动第几个
//            NSInteger moveIndex =scrollView.contentOffset.x/itemWidth;
//            if (moveIndex <self.overlapViews.count) {
//                ///* 运动左边的视图位置
//                for (NSInteger i =0; i<moveIndex; i++) {
//                    UIView *onView =self.redView;
//                    if (i>0) {
//                        onView =self.overlapViews[i-1];
//                    }
//                    UIView *nextView =self.overlapViews[i];
//                    nextView.transform =CGAffineTransformIdentity;
//                    nextView.alpha =1.0;
//                [nextView mas_updateConstraints:^(MASConstraintMaker *make) {
//                        make.left.mas_equalTo(onView.mas_left).offset(itemWidth);
//                    }];
//                }
//                ///* 运动视图位置
//                UIView *onView =self.redView;
//                if (moveIndex>0) {
//                    onView =self.overlapViews[moveIndex-1];
//                }
//                // 相对上一个item 移动了多少
//                CGFloat moveOff_x =scrollView.contentOffset.x - moveIndex*itemWidth;
//                NSLog(@"移动距离:%.2f", moveOff_x);
//
//                UIView *moveView =self.overlapViews[moveIndex];
//            [moveView mas_updateConstraints:^(MASConstraintMaker *make) {
//                    make.left.mas_equalTo(onView.mas_left).offset(moveOff_x+overlapSet);
//                }];
//                // 获取移动视图的默认放大系数
//                CGFloat defScale =(CGRectGetHeight(self.redView.frame)-overlapSet*(moveIndex+1))/CGRectGetHeight(self.redView.frame);
//                // 计算所需的系数
//                CGFloat scale =defScale + (moveOff_x/itemWidth)*(1-defScale);
//                moveView.transform =CGAffineTransformMakeScale(scale, scale);
//                moveView.alpha =1 -(moveIndex+1)*0.15;
//                ///* 运动右边的试图位置
//                for (NSInteger i =moveIndex+1; i<self.overlapViews.count; i++) {
//                    UIView *on1View =self.redView;
//                    if (i>0) {
//                        on1View =self.overlapViews[i-1];
//                    }
//                    UIView *nextView =self.overlapViews[i];
//                [nextView mas_updateConstraints:^(MASConstraintMaker *make) {
//                        make.left.mas_equalTo(on1View.mas_left).offset(overlapSet);
//                    }];
//                }
//            }
//        } else if (scrollView.contentOffset.x<=0) {
//            // 调整恢复视图
//            UIView *tempView =self.redView;
//            for (NSInteger i =0; i<self.overlapViews.count; i++) {
//                UIView *itemView = self.overlapViews[i];
//                itemView.alpha =1 -(i+1)*0.15;
//                CGFloat scale =(CGRectGetHeight(self.redView.frame)-overlapSet*(i+1))/CGRectGetHeight(self.redView.frame);
//                itemView.transform =CGAffineTransformMakeScale(scale, scale);
//            [itemView mas_updateConstraints:^(MASConstraintMaker *make) {
//                    make.left.mas_equalTo(tempView.mas_left).offset(overlapSet);
//                }];
//                tempView =itemView;
//            }
//        } else {
//            // 展开所有视图
//            UIView *tempView =self.redView;
//            for (NSInteger i =0; i<self.overlapViews.count; i++) {
//                UIView *itemView = self.overlapViews[i];
//                itemView.transform =CGAffineTransformIdentity;
//                itemView.alpha =1.0;
//            [itemView mas_updateConstraints:^(MASConstraintMaker *make) {
//                    make.left.mas_equalTo(tempView.mas_left).offset(itemWidth);
//                }];
//                tempView =itemView;
//            }
//        }
//    }
//    /*/ // frame计算
//    for (NSInteger i=3; i>0; i--) {
//        UIView *view =[[UIView alloc] initWithFrame:CGRectMake(CGRectGetMinX(redView.frame)+overlapSet*i, CGRectGetMinY(redView.frame), CGRectGetWidth(redView.frame), CGRectGetHeight(redView.frame))];
//        view.backgroundColor =SET_COLOUR(random()%205, random()%250, random()%245);
//        view.layer.cornerRadius =8.0;
//        view.clipsToBounds =YES;
//        view.alpha =1 -(i+1)*0.15;
//
//        CGFloat scale =(CGRectGetHeight(redView.frame)-overlapSet*i)/CGRectGetHeight(redView.frame);
//        view.transform =CGAffineTransformMakeScale(scale, scale);
//
//        [bgScrollView addSubview:view];
//
//        [self.overlapViews insertObject:view atIndex:0];
//    }
//    [bgScrollView addSubview:redView];
//    self.redView =redView;
//}
//
//- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
//
//    if (scrollView.contentOffset.x>0) {
//        // 每个item所占宽度
//        CGFloat itemWidth =CGRectGetWidth(self.redView.frame)+itemSpace;
//        // 需要移动第几个
//        NSInteger moveIndex =scrollView.contentOffset.x/itemWidth;
//        if (moveIndex <self.overlapViews.count) {
//            // 运动左边的试图位置
//            for (NSInteger i =0; i <moveIndex; i++) {
//                UIView *nextView =self.overlapViews[i];
//                nextView.frame =CGRectMake(CGRectGetMinX(self.redView.frame)+itemSpace+itemWidth *(i+1), nextView.frame.origin.y, nextView.frame.size.width, nextView.frame.size.height);
//            }
//            // 相对上一个item 移动了多少
//            CGFloat moveOff_x =scrollView.contentOffset.x - moveIndex*itemWidth;
//            UIView *moveView =self.overlapViews[moveIndex];
//            moveView.frame =CGRectMake((CGRectGetMinX(self.redView.frame)+overlapSet) +moveOff_x + (itemWidth *moveIndex), moveView.frame.origin.y,  moveView.frame.size.width, moveView.frame.size.height);
//            // 获取移动视图的默认放大系数
//            CGFloat defScale =(CGRectGetHeight(self.redView.frame)-overlapSet*(moveIndex+1))/CGRectGetHeight(self.redView.frame);
//            // 计算所需的系数
//            CGFloat scale =defScale + (moveOff_x/itemWidth)*(1-defScale);
//            moveView.transform =CGAffineTransformMakeScale(scale, scale);
//
//            moveView.alpha =scale;
//            for (NSInteger i =0; i<self.overlapViews.count; i++) {
//                UIView *nextView =self.overlapViews[i];
//                if (i > moveIndex) {
//                    nextView.frame =CGRectMake(CGRectGetMinX(moveView.frame)+overlapSet*i, nextView.frame.origin.y, nextView.frame.size.width, nextView.frame.size.height);
//                } else if (i < moveIndex) {
//                    nextView.frame =CGRectMake(CGRectGetMinX(self.redView.frame)+itemSpace+itemWidth *(i+1), nextView.frame.origin.y, nextView.frame.size.width, nextView.frame.size.height);
//                }
//            }
//            // 运动右边的试图位置
//            for (NSInteger i =moveIndex+1; i<self.overlapViews.count; i++) {
//                UIView *nextView =self.overlapViews[i];
//                nextView.frame =CGRectMake(CGRectGetMinX(moveView.frame)+overlapSet*i, nextView.frame.origin.y, nextView.frame.size.width, nextView.frame.size.height);
//            }
//        }
//    }
//}
////*/
//
//- (NSMutableArray *)overlapViews {
//        if (!_overlapViews) {
//            _overlapViews =[NSMutableArray array];
//        }
//        return _overlapViews;
//    }
///*
//#pragma mark - Navigation
//
//// In a storyboard-based application, you will often want to do a little preparation before navigation
//- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
//    // Get the new view controller using [segue destinationViewController].
//    // Pass the selected object to the new view controller.
//}
//*/
//
//
//
//    @end
//
//
//
//
//}
